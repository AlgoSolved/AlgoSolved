resource "random_string" "password" {
  length  = 16
  special = false
}

resource "aws_db_instance" "algosolved-rdb" {
  depends_on = [
    aws_security_group.algosolved-rds-sg,
    aws_db_subnet_group.algosovled-subnet-group
  ]

  db_name                = var.service
  identifier             = "${var.service}-rdb"
  engine                 = "postgres"
  engine_version         = "16"
  instance_class         = "db.t4g.micro"
  allocated_storage      = 10
  vpc_security_group_ids = [aws_security_group.algosolved-rds-sg.id]
  db_subnet_group_name   = aws_db_subnet_group.algosovled-subnet-group.name

  username = "root"
  password = random_string.password.result

  tags = {
    Project = var.project
    Stage   = var.stage
  }
}

resource "aws_db_subnet_group" "algosovled-subnet-group" {
  name       = "${var.service}-subnet-group"
  subnet_ids = [var.sub_pri_a_id, var.sub_pri_b_id]

  tags = {
    Project = var.project
    Stage   = var.stage
  }
}

output "rdb_password" {
  value = random_string.password.result
}
