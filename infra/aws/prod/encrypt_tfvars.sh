#!/bin/bash

gpg --batch --yes --passphrase $ALGOSOLVED_TFVARS_PASSPHRASE --symmetric --cipher-algo AES256 terraform.tfvars
