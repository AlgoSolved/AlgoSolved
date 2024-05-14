import json
import boto3

asg = boto3.client('autoscaling')

def lambda_handler(event, context):
  desired_capacity = event['detail']['DesiredCapacity']
  auto_scaling_group_name = event['detail']['AutoScalingGroupName']

  print(f"Auto Scaling Group Name: {auto_scaling_group_name}")
  print(f"Desired Capacity: {desired_capacity}")

  update_autoscaling_group(auto_scaling_group_name, desired_capacity)

  return {
    'statusCode': 200,
    'body': json.dumps('Hello from Lambda!')
  }

def update_autoscaling_group(auto_scaling_group_name, desired_capacity):
  try:
    asg_response = asg.update_autoscaling_group(
        AutoScalingGroupName = auto_scaling_group_name,
        DesiredCapacity = desired_capacity
    )
    print(f"Succesed Update DesiredCapacity to {auto_scaling_group_name}")
  except Exception as e:
    print(e)
