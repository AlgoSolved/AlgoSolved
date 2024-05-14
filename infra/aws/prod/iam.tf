resource "aws_iam_role" "lambda_role" {
  name               = "algosolved-lambda-role"
  assume_role_policy = data.aws_iam_policy_document.lambda_assume_role_policy.json
}

data "aws_iam_policy_document" "lambda_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      identifiers = ["lambda.amazonaws.com"]
      type        = "Service"
    }
  }
}

# RDS
resource "aws_iam_policy" "rds_management_policy" {
  name               = "rds-management-policy"
  description        = "Policy to manage RDS instances"
  policy             = data.aws_iam_policy_document.rds_management_policy_doc.json
}

data "aws_iam_policy_document" "rds_management_policy_doc" {
  statement {
    actions = [
      "rds: StartDBInstance",
      "rds: StopDBInstance",
      "rds:DescribeDBInstances"
    ]
    resources = ["*"]
  }
}

# AutoScaling Group
resource "aws_iam_policy" "asg_management_policy" {
  name        = "autoscaling-management-policy"
  description = "Policy to manage AutoScaling groups"
  policy      = data.aws_iam_policy_document.asg_management_policy_doc.json
}

data "aws_iam_policy_document" "asg_management_policy_doc" {
  statement {
    actions = [
      "autoscaling:UpdateAutoScalingGroup"
    ]
    resources = ["*"]
  }
}

resource "aws_iam_role_policy_attachment" "asg_policy_attachment" {
  policy_arn = aws_iam_policy.asg_management_policy.arn
  role       = aws_iam_role.lambda_role.name
}

resource "aws_iam_role_policy_attachment" "rds_policy_attachment" {
  policy_arn = aws_iam_policy.rds_management_policy.arn
  role       = aws_iam_role.lambda_role.name
}
