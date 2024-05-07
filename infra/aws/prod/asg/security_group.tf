resource "aws_security_group" "algosolved-ec2-sg" {
  name = "${var.service}-ec2-sg"
  description = "Allow vpc network"
  vpc_id = var.vpc_id

  ingress {
    from_port = 0
    to_port = 0
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
