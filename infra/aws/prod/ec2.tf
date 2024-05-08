resource "tls_private_key" "private_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "generated_key" {
  key_name   = "algosolved"
  public_key = tls_private_key.private_key.public_key_openssh
}

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

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}

resource "aws_launch_template" "algosolved_launch_template" {
  name                   = "algosolved-launch-template"
  image_id               = data.aws_ami.ubuntu.id
  instance_type          = "t3a.nano"
  key_name               = aws_key_pair.generated_key.key_name
  vpc_security_group_ids = [aws_security_group.algosolved_security_group.id]

  depends_on = [
    aws_security_group.algosolved_security_group,
  ]

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
      Project = "algoSolved"
      Stage   = "prod"
    }
  }

  tag_specifications {
    resource_type = "volume"
    tags = {
      Project = "algoSolved"
      Stage   = "prod"
    }
  }
  tag_specifications {
    resource_type = "network-interface"
    tags = {
      Project = "algoSolved"
      Stage   = "prod"
    }
  }
  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}

resource "aws_autoscaling_group" "algosolved_autoscaling_group" {
  name             = "algosolved_autoscaling-group"
  max_size         = 1
  min_size         = 0
  desired_capacity = 0
  vpc_zone_identifier = [
    aws_subnet.public_1.id,
    aws_subnet.public_2.id,
    aws_subnet.private_1.id,
    aws_subnet.private_2.id
  ]

  depends_on = [
    aws_launch_template.algosolved_launch_template
  ]

  launch_template {
    id      = aws_launch_template.algosolved_launch_template.id
    version = "$Latest"
  }
}
