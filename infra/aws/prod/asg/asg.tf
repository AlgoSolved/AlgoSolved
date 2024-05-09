resource "aws_launch_template" "algosolved-lt" {
  depends_on = [
    aws_security_group.algosolved-ec2-sg
  ]
  name                   = "${var.service}-launch-template"
  image_id               = data.aws_ami.ubuntu.id
  instance_type          = var.ec2_instance_type
  key_name               = "${var.service}-key"
  vpc_security_group_ids = [aws_security_group.algosolved-ec2-sg.id]

  block_device_mappings {
    device_name = "/dev/sdf"
    ebs {
      volume_size = 30
      volume_type = "gp3"
    }
  }
  #  tag_specifications {
  #    resource_type = "instance"
  #    tags = {
  #      Name = ""
  #    }
  #  }
  #  tag_specifications {
  #    resource_type = "network-interface"
  #    tags = {
  #      Name = ""
  #    }
  #  }
  tags = {
    Name = "asg-ec2-template"
  }
}

resource "aws_autoscaling_group" "algosolved-ec2-asg" {
  depends_on = [
    aws_launch_template.algosolved-lt
  ]

  name                = "${var.service}-ec2-asg"
  desired_capacity    = 1
  max_size            = 2
  min_size            = 1
  vpc_zone_identifier = [var.sub_pub_ex1_id, var.sub_pub_ex2_id]
  #  health_check_grace_period = 300
  #  health_check_type = "EC2"
  force_delete = false
  launch_template {
    id      = aws_launch_template.algosolved-lt.id
    version = "$Latest"
  }
}
