terraform {
  backend "s3" {
    bucket = "algosolved-terraform-s3"
    key    = "infra/prod/terraform.tfstate"
    region = "ap-northeast-2"
  }
}
