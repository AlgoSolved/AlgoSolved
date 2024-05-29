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

# RDS
resource "aws_iam_policy" "rds_management_policy" {
  name        = "rds-management-policy"
  description = "Policy to manage RDS instances"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Action" : [
          "rds: StartDBInstance",
          "rds: StopDBInstance",
          "rds: DescribeDBInstances"
        ],
#        "Resource" : "*"
        "Resource" : "arn:aws:rds:*:*:db:*"
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
        "Action" : [
          "autoscaling: UpdateAutoScalingGroup",
          "autoscaling: DescribeAutoScalingGroups"
        ],
        "Resource" : "*"
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
