import json
import os
import boto3

from botocore.exceptions import ClientError

region = os.environ['AWS_REGION']
asg = boto3.client('autoscaling', region_name=region)
rds = boto3.client('rds', region_name=region)

def lambda_handler(event, context):
  # TODO: scale_in 작성하고 scale_out 복붙해서 수정
  rds_resource = event['resources'][0].split(':')[6]
  asg_resource = event['resources'][1].split(':')[5]

  dbs = rds.describe_db_instances(DBInstanceIdentifier=[rds_resource])['DBInstances']
  asgs = asg.describe_auto_scaling_groups(AutoScalingGroupNames=[asg_resource])['AutoScalingGroups']
  for db in dbs:
    if db['DBInstanceStatus'] == 'available':
      response = []
      response.append({
        'DBInstanceIdentifier': db['DBInstanceIdentifier'],
        'DBInstanceStatus': db['DBInstanceStatus']
      })
      return {
        'statusCode': 200,
        'body': json.dumps(response)
      }

    if db['DBInstanceStatus'] == 'stopped':
      try:
        response = []
        response.append(rds.start_db_instances(DBInstanceIdentifier=db['DBInstanceIdentifier']))
        for asg_group in asgs:
          if asg_group['DesiredCapacity'] <= 0:
            response.append(asg.update_auto_scaling_group
                (
                AutoScalingGroupName = asg_group['AutoScalingGroupName'],
                DesiredCapacity = 1
            ))

        return {
          'statusCode': 200,
          'body': json.dumps(response)
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
          'body': json.dumps(response)
        }
