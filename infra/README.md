# AlgoSolved Infra

- tfenv : 3.0.0
- Terraform Version: 1.8.1

### 인프라 구조도

<img width="1026" alt="image" src="https://github.com/AlgoSolved/AlgoSolved/assets/57058726/697c114e-b820-409a-a50e-033438ac1d18">

### 폴더 구조

```

infra
├── README.md
└── aws
    └── prod
        ├── backend.tf
        └── ecr.tf

```

### terraform tfvars

```
# encrypt
gpg --symmetric --cipher-algo AES256 terraform.tfvars

```

```
# decrypt
gpg --output terraform.tfvars --decrypt terraform.tfvars.gpg

```
