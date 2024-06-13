import json
import os
import boto3

from botocore.exceptions import ClientError

region = os.environ['AWS_REGION']
asg = boto3.client('autoscaling', region_name=region)
rds = boto3.client('rds', region_name=region)

def lambda_handler(event, context):
  dbs = rds.describe_db_instances(DBInstanceIdentifier=event['rds_identifier'][0])['DBInstances']
  asgs = asg.describe_auto_scaling_groups(AutoScalingGroupNames=event['asg_name'])['AutoScalingGroups']
  is_success = True

  try:
    rds_response = stop_rds_instances(dbs)
    asg_response = update_autoscaling_group(asgs)

    response = [rds_response, asg_response]
    return {
      'statusCode': 200,
      'body': response
    }
  except ClientError as e:
    is_success = False
    return {
      'statusCode': e.response['ResponseMetadata']['HTTPStatusCode'],
      'body': e.rsponse
    }
  finally:
    slack_alarm(response, is_success and 'success' or 'error')

def stop_rds_instances(dbs):
  response = []

  try:
    for db in dbs:
      db_instance = {
        'DBInstanceIdentifier': db['DBInstanceIdentifier'],
        'DBInstanceStatus': db['DBInstanceStatus']
      }
      if db['DBInstanceStatus'] == 'stopped':
        response.append(db_instance)
      elif db['DBInstanceStatus'] == 'available':
        rds.stop_db_instance(DBInstanceIdentifier=db['DBInstanceIdentifier'])
        response.append(db_instance)
  except ClientError as e:
    raise e

  return response

def update_autoscaling_group(asgs):
  response = []

  try:
    for asg_group in asgs:
      if asg_group['DesiredCapacity'] > 0:
        asg.update_auto_scaling_group(
            AutoScalingGroupName=asg_group['AutoScalingGroupName'],
            DesiredCapacity=0)
        response.append({
          'AutoScalingGroupName': asg_group['AutoScalingGroupName'],
          'DesiredCapacity': asg_group['DesiredCapacity']
        })
  except ClientError as e:
    raise e

  return response


def build_message(result, status):
  current_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
  status_message = ""
  message = ""

  if isinstance(result, list):
    for item in result:
      if isinstance(item, dict):
        for key, value in item.items():
          message += f"\n> {key} : {value}"
      else:
        message = f"\n> {result}"
  else:
    message = f"\n> {result}"

  if status == 'success':
    status_message = "✅ COMPLETED"
  else :
    status_message = "❌ FAILED"

  text_message = (
      f"*[AlgoSolved Scale-In 알람]* \n> *State* : {status_message} \n> "
      + f"*Time* : {current_time} \n> *Message* {message} \n")
  return text_message


def slack_alarm(result, status):
  send_message = {
    'blocks': [
      {
        'type': 'section',
        'text': {
          'type': 'mrkdwn',
          'text': build_message(result, status)
        }
      }
    ]
  }

  return requests.post(
      SLACK_HOOK_URL,
      data=json.dumps(send_message).encode('utf-8'),
      headers={'Content-Type': 'application/json'}
  )