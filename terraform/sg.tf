resource "aws_security_group" "appsg" {
  name        = "${var.env}-${var.appname}-sg"
  description = "trading_signals_app_sg"
  vpc_id      = data.terraform_remote_state.resources.outputs.vpc_id
}

resource "aws_security_group_rule" "appin" {
  type              = "ingress"
  security_group_id = aws_security_group.appsg.id

  from_port   = 0
  to_port     = 0
  protocol    = "-1"
  cidr_blocks = ["0.0.0.0/0"]

}

resource "aws_security_group_rule" "appout" {
  type              = "egress"
  security_group_id = aws_security_group.appsg.id

  from_port   = 0
  to_port     = 0
  protocol    = "-1"
  cidr_blocks = ["0.0.0.0/0"]
}
