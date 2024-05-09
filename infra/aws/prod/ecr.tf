resource "aws_ecr_repository" "algosolved_repository" {
  name = "${var.service}-ecr"

  tags_all = {
    Project = var.project
    Stage   = var.stage
  }
}
