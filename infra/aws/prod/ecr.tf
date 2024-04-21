resource "aws_ecr_repository" "algosolved_repository" {
  name = "algosolved"

  tags_all = {
    Name  = "algosolved"
    Stage = "prod"
  }
}
