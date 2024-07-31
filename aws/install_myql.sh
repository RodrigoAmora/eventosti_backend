#!/bin/bash

echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Installing MySQL ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n\n"

sudo dnf install mariadb105-server

sudo systemctl start mariadb
sudo systemctl enable mariadb

echo -e "\033[01;32m######################\033[01;32m"
echo -e "\033[01;32m### Starting MySQL ###\033[01;32m"
echo -e "\033[01;32m######################\033[01;32m"
echo -e "\n\n\n"

sudo mysql_secure_installation


#mysql -u root -p
