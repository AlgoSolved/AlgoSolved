resource "aws_route53_zone" "algosolved_org_public_zone" {
  name = "algosolved.org"
}

resource "aws_route53_record" "backend_algosolved_org_record" {
  zone_id = aws_route53_zone.algosolved_org_public_zone.zone_id
  name    = "backend.algosolved.org"
  type    = "A"

  alias {
    name                   = aws_lb.algosolved-lb.dns_name
    zone_id                = aws_lb.algosolved-lb.zone_id
    evaluate_target_health = true
  }
}

resource "aws_route53_record" "algosolved_org_record" {
  zone_id         = aws_route53_zone.algosolved_org_public_zone.zone_id
  name            = "algosolved.org"
  type            = "A"
  allow_overwrite = true

  alias {
    name                   = aws_cloudfront_distribution.algosolved-org-cf.domain_name
    zone_id                = aws_cloudfront_distribution.algosolved-org-cf.hosted_zone_id
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

  tags = {
    Project = var.project
    Stage   = var.stage
  }
  tags_all = {
    Project = var.project
    Stage   = var.stage
  }
}

resource "aws_acm_certificate" "algosolved-cert-us" {
  domain_name       = "algosolved.org"
  validation_method = "DNS"
  subject_alternative_names = [
    "*.algosolved.org", "algosolved.org"
  ]
  provider = aws.us-east-1

  lifecycle {
    create_before_destroy = true
    ignore_changes        = [id]
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


