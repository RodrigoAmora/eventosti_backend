#!/bin/bash

#########################################################
### Shellscript to run Pods and Service on Kubernetes ###
#########################################################

rm -rf target/
mvn clean install -Pprod -DskipTests


cd kubernetes/

kubectl apply -f secrets.yaml
kubectl get secrets # List all Secrets

echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Running ConfigMap.... ###\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n\n"

kubectl apply -f app-configmap.yaml
kubectl get cm # List all ConfigMaps


#######

echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Creating PVC.... ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n"

kubectl apply -f pvc.yaml

#######

echo -e "\033[01;32m########################\033[01;32m"
echo -e "\033[01;32m### Running Pods.... ###\033[01;32m"
echo -e "\033[01;32m########################\033[01;32m"
echo -e "\n\n"

kubectl apply -f deployments.yaml

#######

echo -e "\033[01;32m############################\033[01;32m"
echo -e "\033[01;32m### Running Services.... ###\033[01;32m"
echo -e "\033[01;32m############################\033[01;32m"
echo -e "\n\n"

kubectl apply -f services.yaml


#####

echo -e "\n"

kubectl get pods --watch
