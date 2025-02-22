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
  ami                    = "${data.aws_ami.ubuntu.id}"
  instance_type          = "t2.micro"
  key_name               = "${aws_key_pair.auth.id}"
  vpc_security_group_ids = ["${aws_security_group.securitygroup.id}"]
  
  user_data              = <<-EOF
                          #!/bin/bash
                          apt-get update
                          sudo yum install iptables
                          sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8081
                          sudo yum install java-17-amazon-corretto-devel
                          sudo yum install -y maven
                          sudo dnf install mariadb105-server
                          sudo mysql_secure_installation
                          sudo systemctl start mariadb
                          sudo systemctl enable mariadb
                          sudo mysql_secure_installation
                          sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
                          mvn spring-boot:run
                          EOF
}

output "instance_id" {
  description = "ID of the EC2 instance"
  value = aws_instance.app_server.id
}

output "instance_public_ip" {
  description = "Public IP of the EC2 instance"
  value = aws_instance.app_server.public_ip
}