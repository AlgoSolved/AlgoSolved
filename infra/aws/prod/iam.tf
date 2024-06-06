# Lambda iam
resource "aws_iam_role" "lambda_role" {
  name = "scalable-lambda-role"
  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Action" : [
          "sts:AssumeRole"
        ],
        "Principal" : {
          "Service" : "lambda.amazonaws.com"
        },
        "Effect" : "Allow"
      }
    ]
  })
}

resource "aws_iam_role" "scalable_lambda_scheduler_role" {
  name = "scalable-lambda-scheduler-role"
  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Principal" : {
          "Service" : "scheduler.amazonaws.com"
        },
        "Action" : "sts:AssumeRole"
      }
    ]
  })
}

# RDS
resource "aws_iam_policy" "rds_management_policy" {
  name        = "rds-management-policy"
  description = "Policy to manage RDS instances"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "rds:StartDBInstance",
          "rds:StopDBInstance",
          "rds:DescribeDBInstances"
        ],
        "Resource" : "*"
      }
    ]
  })
}

# AutoScaling Group
resource "aws_iam_policy" "asg_management_policy" {
  name        = "autoscaling-management-policy"
  description = "Policy to manage AutoScaling groups"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "autoscaling:UpdateAutoScalingGroup",
          "autoscaling:DescribeAutoScalingGroups"
        ],
        "Resource" : "*"
      }
    ]
  })
}

resource "aws_iam_policy" "scalable_lambda_scheduler_policy" {
  name = "scalable-lambda-scheduler-policy"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "lambda:InvokeFunction"
        ],
        "Resource" : [
          "arn:aws:lambda:ap-northeast-2:471112990651:function:algosolved-scale-in-lambda:*",
          "arn:aws:lambda:ap-northeast-2:471112990651:function:algosolved-scale-in-lambda",
          "arn:aws:lambda:ap-northeast-2:471112990651:function:algosolved-scale-out-lambda:*",
          "arn:aws:lambda:ap-northeast-2:471112990651:function:algosolved-scale-out-lambda"
        ]
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "asg_policy_attachment" {
  policy_arn = aws_iam_policy.asg_management_policy.arn
  role       = aws_iam_role.lambda_role.name
}

resource "aws_iam_role_policy_attachment" "rds_policy_attachment" {
  policy_arn = aws_iam_policy.rds_management_policy.arn
  role       = aws_iam_role.lambda_role.name
}


resource "aws_iam_role_policy_attachment" "scalable_lambda_scheduler_policy_attachment" {
  role       = aws_iam_role.scalable_lambda_scheduler_role.name
  policy_arn = aws_iam_policy.scalable_lambda_scheduler_policy.arn
}
