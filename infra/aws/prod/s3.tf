resource "aws_s3_bucket" "algosolved-fe-bucket" {
  bucket = "algosolved-fe-bucket"

  tags = {
    Name    = "algosolved-fe-bucket"
    Project = var.project
    Stage   = var.stage
  }
}

resource "aws_s3_bucket_policy" "public_bucket_policy" {
  bucket = aws_s3_bucket.algosolved-fe-bucket.bucket
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Principal = "*",
        Action    = "s3:GetObject",
        Resource  = "arn:aws:s3:::${aws_s3_bucket.algosolved-fe-bucket.bucket}/*",
      },
    ],
  })
}

resource "aws_s3_bucket_public_access_block" "public_access_block" {
  bucket = aws_s3_bucket.algosolved-fe-bucket.bucket

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_website_configuration" "algosolved-fe-bucket-website" {
  depends_on = [
    aws_s3_bucket.algosolved-fe-bucket,
  ]
  bucket = aws_s3_bucket.algosolved-fe-bucket.bucket

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "error.html"
  }
}

## 도메인 이름으로 추가
resource "aws_s3_bucket" "algosolved-org" {
  bucket = "algosolved.org"

  tags = {
    Name    = "algosolved.org"
    Project = var.project
    Stage   = var.stage
  }
}

resource "aws_s3_bucket_ownership_controls" "algosolved-org-ownership" {
  bucket = aws_s3_bucket.algosolved-org.id
  rule {
    object_ownership = "ObjectWriter"
  }
}

resource "aws_s3_bucket_acl" "algosolved-org-acl" {
  depends_on = [aws_s3_bucket_ownership_controls.algosolved-org-ownership]

  bucket = aws_s3_bucket.algosolved-org.id
  acl    = "private"
}

resource "aws_s3_bucket_policy" "algosolved-org-policy" {
  bucket = aws_s3_bucket.algosolved-org.bucket
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Principal = "*",
        Action    = "s3:GetObject",
        Resource  = "arn:aws:s3:::${aws_s3_bucket.algosolved-org.bucket}/*",
      },
    ],
  })
}

resource "aws_s3_bucket_public_access_block" "algosolved-org-access" {
  bucket = aws_s3_bucket.algosolved-org.bucket

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_website_configuration" "algosolved-org-website" {
  depends_on = [
    aws_s3_bucket.algosolved-org,
  ]
  bucket = aws_s3_bucket.algosolved-org.bucket

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "error.html"
  }
}


