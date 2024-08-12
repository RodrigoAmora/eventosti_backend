#!/bin/bash

echo "Type name of .jar: "
read nameJar;

echo "Type the presigned URL: " 
read presignedURL;

iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8090

wget -O $nameJar "$presignedURL"

nohup java -jar $nameJar
