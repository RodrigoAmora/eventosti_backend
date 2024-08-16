#!/bin/bash

### Shellscript to up Docker's containers ###

rm -rf target/
mvn clean install

echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\033[01;32m### Building containers ###\033[01;32m"
echo -e "\033[01;32m###########################\033[01;32m"
echo -e "\n\n\n"

sudo docker-compose build

echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Uping containers ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n\n"

sudo docker-compose up -d
