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

  name                = "${var.service}-ec2-asg"
  max_size            = 3
  min_size            = 0
  desired_capacity    = 0
  vpc_zone_identifier = [var.sub_pub_a_id, var.sub_pub_b_id, var.sub_pub_c_id, var.sub_pub_d_id]

  launch_template {
    id      = aws_launch_template.algosolved-lt.id
    version = "$Latest"
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

resource "aws_instance" "algoSolved-ec2" {
  ami = data.aws_ami.ubuntu.id

  launch_template {
    id = aws_launch_template.algosolved-lt.id
  }
  tags = {
    Project = var.project
    Stage   = var.stage
  }
}

