resource "aws_security_group" "algosolved-ec2-sg" {
  name        = "${var.service}-ec2-sgp"
  description = "Algosolved EC2 Security Group"
  vpc_id      = var.vpc_id

  ingress {
    description = "allow ingress from all"
    from_port   = 0
    to_port     = 0
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "allow 443"
    from_port   = 443
    to_port     = 443
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

resource "aws_security_group" "algosolved-rds-sg" {
  name        = "${var.service}-rds-sgp"
  description = "Algosolved RDS Security Group"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 0
    to_port     = 65535
    protocol    = "TCP"
    cidr_blocks = ["172.31.0.0/16"]
  }

  egress {
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
