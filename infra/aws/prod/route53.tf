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


resource "tls_private_key" "algosolved-tls-key" {
  algorithm = "ECDSA"
}

resource "tls_self_signed_cert" "algosolved-tls-cert" {
  key_algorithm   = tls_private_key.algosolved-tls-key.algorithm
  private_key_pem = tls_private_key.algosolved-tls-key.private_key_pem

  validity_period_hours = 12
  early_renewal_hours = 3

  allowed_uses = [
    "key_encipherment",
    "digital_signature",
    "server_auth",
  ]

  dns_names = ["algosolved.org"]

  subject {
    common_name  = "algosolved.org"
    organization = "ACME Examples, Inc"
  }
}

resource "aws_iam_server_certificate" "algosolved-cert" {
  name             = "algosolved_cert"
  certificate_body = tls_self_signed_cert.algosolved-tls-cert.cert_pem
  private_key      = tls_private_key.algosolved-tls-key.private_key_pem
}

