#!/bin/bash

#########################################################
## Shellscript para instalar o Java 17, Maven, MySQL   ##
## e o AWS CLI no EC2 que receberá a aplicação.        ##
#########################################################



#########################################################

iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8090

#########################################################

echo -e "\n\n\n"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Installing Java ####\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n\n"

sudo yum install java-17-amazon-corretto-devel

#########################################################

echo -e "\n\n\n"
echo -e "\033[01;32m#########################\033[01;32m"
echo -e "\033[01;32m### Installing Maven ####\033[01;32m"
echo -e "\033[01;32m#########################\033[01;32m"
echo -e "\n\n\n"

sudo sudo apt install -y maven

#########################################################

echo -e "\n\n\n"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\033[01;32m### Installing Jenkins ####\033[01;32m"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\n\n\n"

sudo wget -O /usr/share/keyrings/jenkins-keyring.asc   https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]"   https://pkg.jenkins.io/debian-stable binary/ | sudo tee   /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt-get update
sudo apt-get install jenkins -y
sudo systemctl enable jenkins
sudo systemctl start jenkins

#########################################################

echo -e "\n\n\n"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\033[01;32m### Installing AWS CLI ####\033[01;32m"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\n\n\n"

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
sudo apt install -y unzip
unzip awscliv2.zip
sudo ./aws/install

#########################################################

echo -e "\n\n\n"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Installing MySQL ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n\n"

sudo dnf install mariadb105-server

sudo mysql_secure_installation

sudo systemctl start mariadb
sudo systemctl enable mariadb

sudo mysql_secure_installation

#mysql -u root -p
