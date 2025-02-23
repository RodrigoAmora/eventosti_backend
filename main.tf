terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-east-1"
  
  access_key = var.AWS_ACCESS_KEY_ID
  secret_key = var.AWS_SECRET_ACCESS_KEY
}

resource "aws_security_group" "securitygroup" {
  name        = "ec2-securitygroup"
  description = "Ingress Http and SSH and Egress to anywhere "

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "eventosti2" {
  ami                    = "ami-0ba9883b710b05ac6"
  instance_type          = "t2.micro"
  vpc_security_group_ids = ["${aws_security_group.securitygroup.id}"]
  user_data              = "${file("./aws/aws_configure_enviroment.sh")}"

}

output "instance_id" {
  description = "ID of the EC2 instance"
  value       = aws_instance.eventosti2.id
}

output "instance_public_ip" {
  description = "Public IP of the EC2 instance"
  value       = aws_instance.eventosti2.public_ip
}

variable "AWS_SECRET_ACCESS_KEY" {
  default = ${{ secrets.AWS_SECRET_ACCESS_KEY }}
}

variable "AWS_ACCESS_KEY_ID" {
  default = ${{ secrets.AWS_ACCESS_KEY_ID }}
}
