#!/bin/bash

rm -rf target/

mvn clean install

sudo docker-compose build

sudo docker-compose up -d
