resource "aws_route53_zone" "public_zone" {
  name = "algosolved.com"
}

resource "aws_route53_record" "algosolved_record" {
  zone_id = aws_route53_zone.public_zone.zone_id
  name    = "algosolved.com"
  type    = "A"

  alias {
    name                   = aws_lb.algosolved-lb.dns_name
    zone_id                = aws_lb.algosolved-lb.zone_id
    evaluate_target_health = true
  }
}


resource "aws_route53_zone" "algosolved_org_public_zone" {
  name = "algosolved.org"
}

resource "aws_route53_record" "algosolved_org_record" {
  zone_id         = aws_route53_zone.algosolved_org_public_zone.zone_id
  name            = "algosolved.org"
  type            = "A"
  allow_overwrite = true

  alias {
    name                   = aws_s3_bucket_website_configuration.algosolved-org-website.website_domain
    zone_id                = aws_s3_bucket.algosolved-org.hosted_zone_id
    evaluate_target_health = true
  }
}

resource "aws_route53_record" "algosolved_org_api_record" {
  zone_id = aws_route53_zone.algosolved_org_public_zone.zone_id
  name    = "api.algosolved.org"
  type    = "A"

  alias {
    name                   = aws_lb.algosolved-lb.dns_name
    zone_id                = aws_lb.algosolved-lb.zone_id
    evaluate_target_health = true
  }
}

resource "aws_acm_certificate" "algosolved-cert" {
  domain_name               = "algosolved.org"
  validation_method         = "DNS"
  subject_alternative_names = ["*.algosolved.org", "algosolved.org"]

  lifecycle {
    create_before_destroy = true
    ignore_changes        = [id]
  }
}


