#!/bin/bash

USER=$(whoami)

# the directory to save the JAR file (e.g., in the home directory)
LOCAL_DIR="/home/${USER}"

# Ensure the local directory exists
mkdir -p "$LOCAL_DIR"

echo "Choose an option:"
echo "1) Download jar file from S3 directly (AWS CLI)"
echo "2) Download jar file from presigned URL"
echo -e "\n\n"
echo "Type your choise: "
read number

jar_file=""

if [ $number -eq 1 ]
then
	echo "Type bucket name: "
	read s3_bucket;
	
	echo "Type the name of jar file: "
	read jar_file;

	if ! [[ $jar_file =~ ".jar" ]]; then
	  	jar_file+=".jar";
	fi

	echo -e "\n\n\n"
	echo -e "\033[01;32m############################\033[01;32m"
	echo -e "\033[01;32m### Downloading JAR file ###\033[01;32m"
	echo -e "\033[01;32m############################\033[01;32m"
	echo -e "\n\n\n"

	aws s3 cp "s3://$s3_bucket/$jar_file" "$LOCAL_DIR/$jar_file"
else
	echo "Type the name of jar file: "
	read jar_file;

	if ! [[ $jar_file =~ ".jar" ]]; then
	  	jar_file+=".jar";
	fi

	echo "Type the presigned URL: " 
	read presignedURL;

	echo -e "\n\n\n"
	echo -e "\033[01;32m############################\033[01;32m"
	echo -e "\033[01;32m### Downloading JAR file ###\033[01;32m"
	echo -e "\033[01;32m############################\033[01;32m"
	echo -e "\n\n\n"

	wget -O $jar_file "$presignedURL"
fi

sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080

nohup java -jar $jar_file
