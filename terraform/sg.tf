resource "aws_security_group" "ecs_app_sg" {
  name        = "${var.env}-${var.app_name}-ecs-sg"
  description = "trading_signals_app_sg"
  vpc_id      = data.terraform_remote_state.resources.outputs.vpc_id
}

resource "aws_security_group_rule" "app_http_inbound" {
  type              = "ingress"
  security_group_id = aws_security_group.ecs_app_sg.id

  from_port   = var.container_port
  to_port     = var.container_port
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]

  }

resource "aws_security_group_rule" "app_all_outbound" {
  type              = "egress"
  security_group_id = aws_security_group.ecs_app_sg.id

  from_port   = 0
  to_port     = 0
  protocol    = "-1"
  cidr_blocks = ["0.0.0.0/0"]
}
