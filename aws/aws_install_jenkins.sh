#!/bin/bash

echo -e "\n\n\n"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\033[01;32m### Installing Jenkins ####\033[01;32m"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\n\n\n"

sudo yum update

sudo wget -O /etc/yum.repos.d/jenkins.repo \
    https://pkg.jenkins.io/redhat-stable/jenkins.repo

sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key

sudo yum upgrade

sudo yum install jenkins -y
sudo systemctl enable jenkins
sudo systemctl start jenkins
