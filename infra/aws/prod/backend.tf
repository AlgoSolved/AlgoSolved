terraform {
  required_version = "1.8.1"

  backend "s3" {
    bucket = "algosolved-terraform-s3"
    key    = "infra/prod/terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "algosolved-terraform-lock"
  }
}
