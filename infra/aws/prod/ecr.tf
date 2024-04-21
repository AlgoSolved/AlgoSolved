resource "aws_ecr_repository" "algosolved_repository" {
  name = "algosolved"

  tags = {
    Name  = "algosolved"
    Stage = "prod"
  }
}
