resource "aws_cloudwatch_log_group" "applogs" {
  name = "${var.env}-${var.appname}-logs"
}

data "template_file" "tasktemplate" {
  template = file("task.json")

  vars = {
    servicename = var.appname
    image       = var.image
    port        = var.port
    region      = var.region
    dbhost      = data.terraform_remote_state.resources.outputs.dbhost
    dbpwd       = data.terraform_remote_state.resources.outputs.dbpswd
    dbuser      = var.dbuser
    dbname      = var.dbname
    logsgroupid = aws_cloudwatch_log_group.applogs.id
    uidomain    = var.uidomain
  }
}

resource "aws_ecs_task_definition" "task_definition" {
  container_definitions    = data.template_file.tasktemplate.rendered
  family                   = "${var.env}_${var.appname}"
  cpu                      = 512
  memory                   = 1024
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  execution_role_arn       = data.terraform_remote_state.resources.outputs.cluster_role_arn
  task_role_arn            = data.terraform_remote_state.resources.outputs.cluster_role_arn
}

resource "aws_ecs_service" "aws-ecs-service" {
  name            = var.appname
  cluster         = data.terraform_remote_state.resources.outputs.clustername
  task_definition = aws_ecs_task_definition.task_definition.id
  launch_type     = "FARGATE"
  desired_count   = 2


  network_configuration {
    subnets = [
      data.terraform_remote_state.resources.outputs.private_subnet_a_id,
      data.terraform_remote_state.resources.outputs.private_subnet_b_id,
      data.terraform_remote_state.resources.outputs.private_subnet_c_id
    ]
    assign_public_ip = false
    security_groups = [
      aws_security_group.appsg.id
    ]

  }

  load_balancer {
    target_group_arn = aws_alb_target_group.apptg.arn
    container_name   = var.appname
    container_port   = var.port
  }

}
