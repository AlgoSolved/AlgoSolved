resource "aws_security_group" "algosolved-ec2-sg" {
  name        = "${var.service}-ec2-sgp"
  description = "Allow vpc network"
  vpc_id      = var.vpc_id

  ingress {
    description = "allow ingress from all"
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "allow egress from all"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Project = var.project
    Stage   = var.stage
  }
}
