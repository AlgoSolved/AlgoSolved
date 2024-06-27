set shell := ["bash", "-uc"]

algosolved_tfvars_passphrase := env_var('ALGOSOLVED_TFVARS_PASSPHRASE')

home_dir := env_var('HOME')

alias te := terraform-encrypt
alias ta := terraform-apply
alias tp := terraform-plan
alias up := docker-compose-up

_default:
    @just --list

terraform-encrypt:
    gpg --batch --yes --passphrase "{{algosolved_tfvars_passphrase}}" -o infra/aws/prod/terraform.tfvars.gpg --symmetric --cipher-algo AES256 infra/aws/prod/terraform.tfvars

docker-compose-up:
    docker-compose up -d

lint:
  java -jar backend/google-java-format.jar --aosp --replace backend/src/**/*

terraform-apply:
  cd infra/aws/prod && terraform apply -auto-approve

terraform-plan:
  cd infra/aws/prod && terraform plan
