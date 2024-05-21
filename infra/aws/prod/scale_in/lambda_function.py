import json
import boto3

asg = boto3.client('autoscaling')
rds = boto3.client('rds')

def lambda_handler(event, context):
  desired_capacity = event['detail']['DesiredCapacity']
  auto_scaling_group_name = event['detail']['AutoScalingGroupName']

  rds_instances = event['resources'][0]
  action = event['detail']['Action']

  print(f"Event details:\n"
        f"  Auto Scaling Group: {auto_scaling_group_name}\n"
        f"  Desired Capacity: {desired_capacity}\n"
        f"  RDS Instance Identifier: {rds_instances}\n"
        f"  Action: {action}")

  if action == 'stop':
    stop_rds_instances(rds_instances)
    update_autoscaling_group(auto_scaling_group_name, desired_capacity)

  return {
    'statusCode': 200,
    'body': json.dumps('Success: Server count 0 and ASG desired capacity 0')
  }

def stop_rds_instances(rds_instances):
  try:
    rds.stop_db_instances(DBInstanceIdentifier=rds_instances)
    print(f"Successfully started RDS instance:{rds_instances}")
  except Exception as e:
    print(e)


def update_autoscaling_group(auto_scaling_group_name, desired_capacity):
  try:
    asg.update_autoscaling_group(
        AutoScalingGroupName = auto_scaling_group_name,
        DesiredCapacity = desired_capacity
    )
    print(f"Successfully updated ASG DesiredCapacity: {desired_capacity}")
  except Exception as e:
    print(e)
