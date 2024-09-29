#!/bin/bash

#########################################################
## Shellscript para instalar o Java 17, Maven, MySQL   ##
## e o AWS CLI no EC2 que receberá a aplicação.        ##
#########################################################


#########################################################

echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Installing IP Tables ####\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n\n"

sudo yum install iptables
sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8081

#########################################################

echo -e "\n\n"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Installing Java ####\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n"

sudo yum install java-17-amazon-corretto-devel
#sudo apt install java-17-amazon-corretto-devel

#########################################################

echo -e "\n\n"
echo -e "\033[01;32m#########################\033[01;32m"
echo -e "\033[01;32m### Installing Maven ####\033[01;32m"
echo -e "\033[01;32m#########################\033[01;32m"
echo -e "\n\n"

sudo yum install -y maven
#sudo apt install -y maven

#########################################################

echo -e "\n\n"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Installing MySQL ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n"

sudo dnf install mariadb105-server

sudo mysql_secure_installation

sudo systemctl start mariadb
sudo systemctl enable mariadb

sudo mysql_secure_installation

#mysql -u root -p

#########################################################

echo -e "\n\n"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\033[01;32m### Installing AWS CLI ####\033[01;32m"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\n\n"

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"

sudo yum install -y unzip
#sudo apt install -y unzip

unzip awscliv2.zip
sudo ./aws/install

#########################################################
