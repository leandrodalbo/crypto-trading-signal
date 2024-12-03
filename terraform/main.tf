terraform {
  backend "s3" {}
}

provider "aws" {
  region     = var.region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

data "terraform_remote_state" "resources" {
  backend = "s3"
  config = {
    region = "${var.region}"
    bucket = "${var.resources_bucket}"
    key    = "${var.resources_bucket_key}"
  }
}

