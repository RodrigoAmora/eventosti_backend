terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "4.52.0"
    }
  }
  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-east-1"
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
  
  user_data              = <<-EOF
              #!/bin/bash
              apt-get update
              apt-get install -y apache2
              sed -i -e 's/80/8080/' /etc/apache2/ports.conf
              echo "<style> body {background-color: black;}</style><img style="text-align:center" src="https://postech.fiap.com.br/gifs/loader.gif"><img src="https://postech.fiap.com.br/imgs/fiap-plus-alura/fiap_alura.png">" > /var/www/html/index.html
              systemctl restart apache2
              EOF
  vpc_security_group_ids = ["${aws_security_group.securitygroup.id}"]
}

output "instance_id" {
  description = "ID of the EC2 instance"
  value = aws_instance.app_server.id
}

output "instance_public_ip" {
  description = "Public IP of the EC2 instance"
  value = aws_instance.app_server.public_ip
}