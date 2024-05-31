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
