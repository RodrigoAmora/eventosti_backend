#!/bin/bash

echo "Type name of .jar: "
read nameJar;

echo "Type the presigned URL: " 
read presignedURL;

wget -O $nameJar "$presignedURL"

nohup java -jar $nameJar
