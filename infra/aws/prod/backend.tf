terraform {
  required_version = "1.8.1"

  backend "s3" {
    bucket = "algosolved-terraform-s3"
    key    = "prod/terraform.tfstate"
    region = "ap-northeast-2"
  }
}

provider "aws" {
  region = "ap-northeast-2"
}
