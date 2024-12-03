resource "aws_iam_role" "fargate_role" {
  name               = "${var.app_name}-rl"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
          "Service": [
            "ecs.amazonaws.com",
            "ecs-tasks.amazonaws.com"
          ]
      },
      "Action": "sts:AssumeRole"
    }
  ] 
}
EOF
}

resource "aws_iam_role_policy" "fargate_role_policy" {
  name   = "${var.app_name}-pol"
  role   = aws_iam_role.fargate_role.id
  policy = <<EOF
    {
      "Version": "2012-10-17",
      "Statement": [
      {
        "Effect": "Allow",
        "Action": [
          "ecs:*",
          "ec2:*",
          "elasticloadbalancing:*",
          "ecr:*",
          "rds:*",
          "sns:*",
          "ssm:*",
          "s3:*",
          "cloudwatch:*",
          "logs:*"
        ],
        "Resource":"*"
      }
    ] 
  }
  EOF
}