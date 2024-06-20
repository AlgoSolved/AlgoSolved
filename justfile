set shell := ["bash", "-uc"]

algosolved_tfvars_passphrase := env_var('ALGOSOLVED_TFVARS_PASSPHRASE')

home_dir := env_var('HOME')

alias te := terraform-encrypt

_default:
    @just --list

terraform-encrypt:
    gpg --batch --yes --passphrase "{{algosolved_tfvars_passphrase}}" -o infra/aws/prod/terraform.tfvars.gpg --symmetric --cipher-algo AES256 infra/aws/prod/terraform.tfvars
