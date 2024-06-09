import json
import os
import boto3
import requests
from datetime import datetime

from botocore.exceptions import ClientError

SLACK_HOOK_URL = os.environ['SLACK_WEBHOOK_URL']

region = os.environ['AWS_REGION']
asg = boto3.client('autoscaling', region_name=region)
rds = boto3.client('rds', region_name=region)

def lambda_handler(event, context):
  dbs = rds.describe_db_instances(DBInstanceIdentifier=event['rds_identifier'][0])['DBInstances']
  asgs = asg.describe_auto_scaling_groups(AutoScalingGroupNames=event['asg_name'])['AutoScalingGroups']

  for db in dbs:
    if db['DBInstanceStatus'] == 'stopped':
      response = []
      response.append({
        'DBInstanceIdentifier': db['DBInstanceIdentifier'],
        'DBInstanceStatus': db['DBInstanceStatus']
      })

      slack_alarm(response, 'success')

      return {
        'statusCode': 200,
        'body': json.dumps(response)
      }

    # TODO: DB 랑 ASG 분리할 것

    if db['DBInstanceStatus'] == 'available':
      try:
        response = []
        response.append(rds.stop_db_instance(DBInstanceIdentifier=db['DBInstanceIdentifier']))
        for asg_group in asgs:
          if asg_group['DesiredCapacity'] > 0:
            response.append(asg.update_auto_scaling_group
                (
                AutoScalingGroupName = asg_group['AutoScalingGroupName'],
                DesiredCapacity = 0
            ))

        slack_alarm(response, 'success')

        return {
          'statusCode': 200,
          'body': json.dumps(response.body)
        }
      except ClientError as e:
        error_response = e.response['Error']
        error_code = error_response['Code']
        error_message = error_response['Message']
        response_metadata = e.response['ResponseMetadata']

        response = {
          'Error': {
            'Code': error_code,
            'Message': error_message
          },
          'ResponseMetadata': response_metadata
        }

        slack_alarm(error_message, 'error')

        return {
          'statusCode': e.response['ResponseMetadata']['HTTPStatusCode'],
          'body': json.dumps(response.body)
        }


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
