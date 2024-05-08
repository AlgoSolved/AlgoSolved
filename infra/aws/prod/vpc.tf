resource "aws_vpc" "algosolved_vpc" {
  cidr_block       = "10.0.0.0/16"
  enable_dns_hostnames = true

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}

# subnets
resource "aws_subnet" "public_1" {
  vpc_id     = aws_vpc.algosolved_vpc.id
  cidr_block = "10.0.1.0/24"

  availability_zone = "ap-northeast-2a"

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}

resource "aws_subnet" "public_2" {
  vpc_id     = aws_vpc.algosolved_vpc.id
  cidr_block = "10.0.2.0/24"

  availability_zone = "ap-northeast-2b"

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}

resource "aws_subnet" "private_1" {
  vpc_id     = aws_vpc.algosolved_vpc.id
  cidr_block = "10.0.102.0/24"

  availability_zone = "ap-northeast-2c"

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}


resource "aws_subnet" "private_2" {
  vpc_id     = aws_vpc.algosolved_vpc.id
  cidr_block = "10.0.103.0/24"

  availability_zone = "ap-northeast-2d"

  tags = {
    Project = "algoSolved"
    Stage   = "prod"
  }
}
