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

  for db in dbs:
    if db['DBInstanceStatus'] == 'stopped':
      response = []
      response.append({
        'DBInstanceIdentifier': db['DBInstanceIdentifier'],
        'DBInstanceStatus': db['DBInstanceStatus']
      })
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

        return {
          'statusCode': e.response['ResponseMetadata']['HTTPStatusCode'],
          'body': json.dumps(response.body)
        }
