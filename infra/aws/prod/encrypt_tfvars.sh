#!/bin/bash

ALGOSOLVED_TFVARS_PASSPHRASE='129eh!ej0fsd'
gpg --batch --yes --passphrase $ALGOSOLVED_TFVARS_PASSPHRASE --symmetric --cipher-algo AES256 terraform.tfvars
