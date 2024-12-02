resource "aws_ecs_cluster" "signals_cluster" {
  name = "${var.env}-${var.app_name}-cluster"
 
  tags = {
    Name        = "${var.env}-${var.app_name}-ecs"
    Environment = var.env
  }
}


data "template_file" "tasktemplate" {
  template = file("task.json")

  vars = {
    image_url             = var.ecs_image_url
    container_port        = var.container_port
    ecs_service_name      = var.app_name
    region                = var.region
    db_user               = var.postgres_user_name
    db_password           = var.postgres_user_password
    db_name               = var.postgres_db_name
    db_host               = data.terraform_remote_state.resources.outputs.dbhost

  }

}


resource "aws_ecs_task_definition" "signals_task_definition" {
  container_definitions    = data.template_file.tasktemplate.rendered
  family = "${var.app_name}-task"
  cpu                      = 512
  memory                   = 2048
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  execution_role_arn       = aws_iam_role.fargate_role.arn
  task_role_arn            = aws_iam_role.fargate_role.arn

}



resource "aws_cloudwatch_log_group" "app_log_group" {
  name = "${var.app_name}-logs"
}

resource "aws_ecs_service" "aws-ecs-service" {
  name                 = "${var.env}-${var.app_name}-ecs-service"
  cluster              = aws_ecs_cluster.signals_cluster.id
  task_definition      = aws_ecs_task_definition.signals_task_definition.arn
  launch_type          = "FARGATE"
  scheduling_strategy  = "REPLICA"
  desired_count        = 2
  force_new_deployment = true

  network_configuration {
    subnets          = [data.terraform_remote_state.resources.outputs.private_subnet_a_id, data.terraform_remote_state.resources.outputs.private_subnet_b_id]
    assign_public_ip = false
    security_groups = [
      aws_security_group.service_security_group.id,
      data.terraform_remote_state.resources.outputs.alb_sg_id
    ]
  }

  load_balancer {
    target_group_arn = data.terraform_remote_state.resources.outputs.alb_tg_arn
    container_name   = "${var.env}-${var.app_name}-container"
    container_port   = var.container_port
  }

}
