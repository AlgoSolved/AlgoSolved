resource "aws_cloudfront_origin_access_identity" "algosolved-origin-access" {
  comment = "algosolved-org"
}

resource "aws_cloudfront_distribution" "algosolved-org-cf" {
  origin {
    origin_id                = aws_s3_bucket.algosolved-org.id
    domain_name              = aws_s3_bucket_website_configuration.algosolved-org-website.website_endpoint

    s3_origin_config {
      origin_access_identity = aws_cloudfront_origin_access_identity.algosolved-origin-access.cloudfront_access_identity_path
    }

    custom_origin_config {
      http_port              = 80
      https_port             = 443
      origin_protocol_policy = "https-only"
      origin_ssl_protocols   = [
        "SSLv3",
        "TLSv1",
        "TLSv1.1",
        "TLSv1.2",]
    }
  }

  aliases = ["algosolved.org"]
  comment             = "algosolved.org"
  enabled             = true
  is_ipv6_enabled     = true
  default_root_object = "index.html"
  http_version        = "http2"

  default_cache_behavior {
    allowed_methods  = ["GET", "HEAD", "OPTIONS"]
    cached_methods   = ["GET", "HEAD", "OPTIONS"]
    target_origin_id = "${aws_s3_bucket.algosolved-org.id}"

    forwarded_values {
      query_string = false

      cookies {
        forward = "none"
      }
    }

    viewer_protocol_policy = "redirect-to-https"
    min_ttl                = 0
    max_ttl                = 360
    default_ttl            = 60
  }

  price_class = "PriceClass_All"

  restrictions {
    geo_restriction {
        restriction_type = "none"
    }
  }

  viewer_certificate {
    acm_certificate_arn = aws_acm_certificate.algosolved-cert.arn
    cloudfront_default_certificate = false
    minimum_protocol_version = "TLSv1.2_2021"
    ssl_support_method = "sni-only"
  }

  tags = {
    Project = var.project
    Stage   = var.stage
  }
  tags_all = {
    Project = var.project
    Stage   = var.stage
  }
}