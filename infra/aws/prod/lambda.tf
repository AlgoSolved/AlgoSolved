resource "aws_lambda_function" "scale_in_lambda" {
  filename         = "./scale_in/${var.lambda_function_name}.zip"
  function_name    = "${var.service}-scale-in-lambda"
  role             = aws_iam_role.lambda_role.arn
  handler          = "${var.lambda_function_name}.lambda_handler"
  timeout          = "600"
  source_code_hash = filebase64sha256("./scale_in/${var.lambda_function_name}.zip")
  runtime          = "python3.12"

  tags = (
    {
      Project  = var.project
      Stage    = var.stage
    }
  )

  depends_on = [
    aws_iam_role.lambda_role
  ]
}

resource "aws_lambda_function" "scale_out_lambda" {
  filename         = "./scale_out/${var.lambda_function_name}.zip"
  function_name    = "${var.service}-scale-out-lambda"
  role             = aws_iam_role.lambda_role.arn
  handler          = "${var.lambda_function_name}.lambda_handler"
  timeout          = "600"
  source_code_hash = filebase64sha256("./scale_out/${var.lambda_function_name}.zip")
  runtime          = "python3.12"

  tags = (
    {
      Project  = var.project
      Stage    = var.stage
    }
  )

  depends_on = [
    aws_iam_role.lambda_role
  ]
}

resource "aws_cloudwatch_event_rule" "scale_in_rule" {
  name        = "${var.service}-scale-in-rule"
  description = "Event bridge scale-in lambda"
  event_pattern = jsonencode({
    "detail" : {
      "DesiredCapacity" : [0],
      "AutoScalingGroupName" : ["algosolved-ec2-asg"]
    },
    "resources" : [
      // aws_db_instance.algosolved-rdb.arn rds pr 머지 후 변경
      "arn:aws:rds:us-west-2:123456789012:db:algosolved-rds"
    ]
  })
}

resource "aws_cloudwatch_event_target" "scale_in_target" {
  arn  = aws_lambda_function.scale_in_lambda.arn
  rule = aws_cloudwatch_event_rule.scale_in_rule.name
}

resource "aws_cloudwatch_event_rule" "scale_out_rule" {
  name        = "${var.service}-scale-out-rule"
  description = "Event bridge scale-out lambda"
  event_pattern = jsonencode({
    "detail" : {
      "DesiredCapacity" : [1],
      "AutoScalingGroupName" : ["algosolved-ec2-asg"],
    },
    "resources" : [
      // aws_db_instance.algosolved-rdb.arn rds pr 머지 후 변경
      "arn:aws:rds:us-west-2:123456789012:db:algosolved-rds"
    ]
  })
}

resource "aws_cloudwatch_event_target" "scale_out_target" {
  arn  = aws_lambda_function.scale_out_lambda.arn
  rule = aws_cloudwatch_event_rule.scale_out_rule.name
}

resource "aws_lambda_permission" "scale_in_lambda_perm" {
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.scale_in_lambda.function_name
  principal     = "events.amazonaws.com"
  source_arn    = aws_cloudwatch_event_rule.scale_in_rule.arn
}

resource "aws_lambda_permission" "scale_out_lambda_perm" {
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.scale_out_lambda.function_name
  principal     = "events.amazonaws.com"
  source_arn    = aws_cloudwatch_event_rule.scale_out_rule.arn
}
