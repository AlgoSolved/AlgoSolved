resource "aws_scheduler_schedule" "scale-in-schedule" {
  name = "scale-in-schedule"

  flexible_time_window {
    mode = "OFF"
  }

  schedule_expression = "cron(0 12 * * ? *)"

  target {
    arn      = aws_lambda_function.scale_in_lambda.arn
    role_arn = aws_iam_role.scalable_lambda_scheduler_role.arn

    input = jsonencode({
      "rds_identifier" : [aws_db_instance.algosolved-rdb.identifier],
      "asg_name" : [aws_autoscaling_group.algosolved-ec2-asg.name],
    })
  }
}

resource "aws_scheduler_schedule" "scale-out-schedule" {
  name = "scale-out-schedule"

  flexible_time_window {
    mode = "OFF"
  }

  schedule_expression = "cron(0 9 * * ? *)"

  target {
    arn      = aws_lambda_function.scale_out_lambda.arn
    role_arn = aws_iam_role.scalable_lambda_scheduler_role.arn

    input = jsonencode({
      "rds_identifier" : [aws_db_instance.algosolved-rdb.identifier],
      "asg_name" : [aws_autoscaling_group.algosolved-ec2-asg.name],
    })
  }
}
