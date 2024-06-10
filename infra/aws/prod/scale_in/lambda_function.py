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

  try:
    rds_response = stop_rds_instances(dbs)
    asg_response = update_autoscaling_group(asgs)

    response = [rds_response, asg_response]
    return {
      'statusCode': 200,
      'body': response
    }
  except ClientError as e:
    return {
      'statusCode': e.response['ResponseMetadata']['HTTPStatusCode'],
      'body': e.rsponse
    }

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
