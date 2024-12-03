resource "aws_alb_target_group" "apptg" {
  name        = "${var.env}-${var.appname}-app-tg"
  port        = var.port
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = data.terraform_remote_state.resources.outputs.vpc_id

  health_check {
    path                = "/actuator/health"
    interval            = "300"
    protocol            = "HTTP"
    matcher             = "200"
    timeout             = "3"
    healthy_threshold   = "2"
    unhealthy_threshold = "2"
  }

  tags = {
    Name        = "${var.appname}-app-tg"
    Environment = var.env
  }
}


resource "aws_alb_listener" "alb-listener" {
  load_balancer_arn = data.terraform_remote_state.resources.outputs.alb_arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.apptg.arn
  }

  depends_on = [aws_alb_target_group.apptg]
}

resource "aws_cloudwatch_log_group" "applogs" {
  name = "${var.env}-${var.appname}-logs"
}