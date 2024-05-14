resource "aws_cloudwatch_log_group" "scale_in_log_group" {
  name              = "/aws/lambda/scale_in/${var.lambda_function_name}"
  retention_in_days = 14
}

resource "aws_cloudwatch_log_group" "scale_out_log_group" {
  name              = "/aws/lambda/scale_out/${var.lambda_function_name}"
  retention_in_days = 14
}
