import json
import boto3

asg = boto3.client('autoscaling')
rds = boto3.client('rds')

def lambda_handler(event, context):
  desired_capacity = event['detail']['DesiredCapacity']
  auto_scaling_group_name = event['detail']['AutoScalingGroupName']

  rds_instances = event['resources'][0].split(':')[-1]
  action = event['detail']['Action']

  print(f"Auto Scaling Group Name: {auto_scaling_group_name}")
  print(f"Desired Capacity: {desired_capacity}")
  print(f"RDS Instances: {rds_instances}")
  print(f"Action: {action}")

  if action == 'stop':
    stop_rds_instances(rds_instances)
    update_autoscaling_group(auto_scaling_group_name, desired_capacity)

  return {
    'statusCode': 200,
    'body': json.dumps('Hello from Lambda!')
  }

def stop_rds_instances(rds_instances):
  try:
    rds.stop_rds_instances(DBInstanceIdentifier=rds_instances)
    print(f"Success: stop_rds_instances {rds_instances}")
  except Exception as e:
    print(e)


def update_autoscaling_group(auto_scaling_group_name, desired_capacity):
  try:
    asg_response = asg.update_autoscaling_group(
        AutoScalingGroupName = auto_scaling_group_name,
        DesiredCapacity = desired_capacity
    )
    print(f"Succesed Update DesiredCapacity to {auto_scaling_group_name}")
  except Exception as e:
    print(e)
