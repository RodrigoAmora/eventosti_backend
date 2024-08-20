#!/bin/bash

echo "Type name of .jar: "
read nameJar;

## Verfica se o nome do Jar contém a extensão .jar
if ! [[ $nameJar =~ ".jar" ]]; then
  	nameJar+=".jar";
fi

echo "Type the presigned URL: " 
read presignedURL;

wget -O $nameJar "$presignedURL"

## Redireciona a porta.
sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8081

## Extrai o jar e excetua a aplicação em segubdo plano.
nohup java -jar $nameJar
