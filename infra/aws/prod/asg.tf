data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-*-22.04-amd64-server-*"]
  }

  filter {
    name   = "owner-alias"
    values = ["amazon"]
  }
}

output "latest_ami_id" {
  value       = data.aws_ami.ubuntu.id
  description = "The AMI ID latest ubuntu 22.04 verison"
}

resource "aws_ssm_parameter" "aws_access_key_id" {
  name = "/config/algosolved_prod/aws.access.key.id"
  type = "String"
  value = "existing_aws_access_key_id"

  lifecycle {
    ignore_changes = [
      value
    ]
  }
}

resource "aws_ssm_parameter" "aws_secret_access_key" {
  name = "/config/algosolved_prod/aws.secret.access.key"
  type = "String"
  value = "existing_aws_secret_access_key"

  lifecycle {
    ignore_changes = [
      value
    ]
  }
}

resource "aws_launch_template" "algosolved-lt" {
  depends_on = [
    aws_security_group.algosolved-ec2-sg,
  ]
  name                   = "${var.service}-launch-template"
  description            = "This is ASG for Algosolved EC2 instances."
  image_id               = data.aws_ami.ubuntu.id
  instance_type          = var.ec2_instance_type
  key_name               = var.ec2_key
  vpc_security_group_ids = [aws_security_group.algosolved-ec2-sg.id]
  user_data = base64encode(templatefile("./launch_template.sh", {
    AWS_ACCESS_KEY_ID     = aws_ssm_parameter.aws_access_key_id.value
    AWS_SECRET_ACCESS_KEY = aws_ssm_parameter.aws_secret_access_key.value
  }))

  block_device_mappings {
    device_name = "/dev/xvda"

    ebs {
      volume_type = "gp3"
      volume_size = 50
    }
  }

  tag_specifications {
    resource_type = "instance"
    tags = {
      Project = var.project
      Stage   = var.stage
    }
  }

  tag_specifications {
    resource_type = "volume"
    tags = {
      Project = var.project
      Stage   = var.stage
    }
  }
  tag_specifications {
    resource_type = "network-interface"
    tags = {
      Project = var.project
      Stage   = var.stage
    }
  }
  tags = {
    Project = var.project
    Stage   = var.stage
  }
}

resource "aws_autoscaling_group" "algosolved-ec2-asg" {
  depends_on = [
    aws_launch_template.algosolved-lt
  ]

  name                 = "${var.service}-ec2-asg"
  max_size             = 3
  min_size             = 0
  desired_capacity     = 1
  vpc_zone_identifier  = [var.sub_pub_a_id, var.sub_pub_b_id, var.sub_pub_c_id, var.sub_pub_d_id]
  target_group_arns    = [aws_lb_target_group.algosolved-lb-tg.arn]
  termination_policies = ["OldestInstance"]

  launch_template {
    id      = aws_launch_template.algosolved-lt.id
    version = aws_launch_template.algosolved-lt.latest_version
  }

  tag {
    key                 = "Project"
    value               = var.project
    propagate_at_launch = true
  }

  tag {
    key                 = "Stage"
    value               = var.stage
    propagate_at_launch = true
  }
}

