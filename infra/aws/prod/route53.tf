#resource "aws_route53_zone" "public_zone" {
#  name = "algosolved.com"
#}
#
#resource "aws_route53_record" "algosolved_record" {
#  zone_id = aws_route53_zone.public_zone.zone_id
#  name    = "algosolved.com"
#  type    = "A"
#
#  alias {
#    name                   = aws_lb.algosolved-lb.dns_name
#    zone_id                = aws_lb.algosolved-lb.zone_id
#    evaluate_target_health = true
#  }
#}
