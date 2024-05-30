resource "aws_lb" "algosolved-lb" {
  name               = "algosolved-alb"
  load_balancer_type = "application"
  idle_timeout       = 120
  ip_address_type    = "ipv4"

  security_groups = [
    aws_security_group.algosolved-ec2-sg.id
  ]

  subnets = [
    var.sub_pub_a_id,
    var.sub_pub_b_id,
    var.sub_pub_c_id,
    var.sub_pub_d_id,
  ]

  timeouts {}

  tags = {
    Project = var.project
    Stage   = var.stage
  }
  tags_all = {
    Project = var.project
    Stage   = var.stage
  }
}


// target-group
resource "aws_lb_target_group" "algosolved-lb-tg" {
  name                 = "algosolved-ec2-target-group"
  deregistration_delay = 15
  protocol             = "HTTP"
  port                 = 80
  vpc_id               = var.vpc_id

  health_check {
    healthy_threshold   = 2
    interval            = 30
    port                = "traffic-port"
    path                = "/api/health/ping"
    unhealthy_threshold = 10
    matcher             = 200
    timeout             = 10
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

// 리스너
resource "aws_alb_listener" "algosolved-http-forward" {
  load_balancer_arn = aws_lb.algosolved-lb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port        = "443"
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
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

// TODO : HTTPS 를 지원하기 위해 인증서가 필요함(domain-CRA)
# resource "aws_acm_certificate" "algosolved-cert" {
#   domain_name = "example.com"
#   validation_method = "DNS"
# }

# resource "aws_alb_listener" "algosolved-https-listener" {
#   load_balancer_arn = aws_lb.algosolved-lb.arn
#   port              = 443
#   protocol          = "HTTPS"
#   ssl_policy        = "ELBSecurityPolicy-TLS-1-2-2017-01"

#   default_action {
#     target_group_arn = aws_lb_target_group.algosolved-lb-tg.arn
#     type             = "forward"
#   }

#   timeouts {}

#   tags = {
#     Project = var.project
#     Stage   = var.stage
#   }
#   tags_all = {
#     Project = var.project
#     Stage   = var.stage
#   }
# }

