data "archive_file" "scale_in_lambda_to_zip" {
  type        = "zip"
  source_file = "./scale_in/${var.lambda_function_filename}"
  output_path = "./scale_in/${var.lambda_function_filename}.zip"
}

data "archive_file" "scale_out_lambda_to_zip" {
  type        = "zip"
  source_file = "./scale_out/${var.lambda_function_filename}"
  output_path = "./scale_out/${var.lambda_function_filename}.zip"
}

resource "aws_lambda_function" "scale_in_lambda" {
  filename         = data.archive_file.scale_in_lambda_to_zip.output_path
  function_name    = "${var.service}-scale-in-lambda"
  role             = aws_iam_role.lambda_role.arn
  handler          = "lambda_function.lambda_handler"
  timeout          = "600"
  source_code_hash = data.archive_file.scale_in_lambda_to_zip.output_base64sha256
  runtime          = "python3.12"

  tags = {
    Project = var.project
    Stage   = var.stage
  }

  depends_on = [
    aws_iam_role.lambda_role
  ]
}

resource "aws_lambda_function" "scale_out_lambda" {
  filename         = data.archive_file.scale_out_lambda_to_zip.output_path
  function_name    = "${var.service}-scale-out-lambda"
  role             = aws_iam_role.lambda_role.arn
  handler          = "lambda_function.lambda_handler"
  timeout          = "600"
  source_code_hash = data.archive_file.scale_out_lambda_to_zip.output_base64sha256
  runtime          = "python3.12"

  tags = {
    Project = var.project
    Stage   = var.stage
  }

  depends_on = [
    aws_iam_role.lambda_role
  ]
}

resource "aws_cloudwatch_event_rule" "scale_in_rule" {
  name        = "${var.service}-scale-in-rule"
  description = "Event bridge scale-in lambda"
  event_pattern = jsonencode({
    "rds_identifier" : [aws_db_instance.algosolved-rdb.identifier],
    "asg_name" : [aws_autoscaling_group.algosolved-ec2-asg.name],
  })
  depends_on = [
    aws_db_instance.algosolved-rdb,
    aws_autoscaling_group.algosolved-ec2-asg
  ]
}

resource "aws_cloudwatch_event_target" "scale_in_target" {
  arn  = aws_lambda_function.scale_in_lambda.arn
  rule = aws_cloudwatch_event_rule.scale_in_rule.name
}

resource "aws_cloudwatch_event_rule" "scale_out_rule" {
  name        = "${var.service}-scale-out-rule"
  description = "Event bridge scale-out lambda"
  event_pattern = jsonencode({
    "rds_identifier" : [aws_db_instance.algosolved-rdb.identifier],
    "asg_name" : [aws_autoscaling_group.algosolved-ec2-asg.name],
  })
  depends_on = [
    aws_db_instance.algosolved-rdb,
    aws_autoscaling_group.algosolved-ec2-asg
  ]
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
