#!/bin/bash

##1. 도커 실행을 위한 도커 설치
export AWS_ACCESS_KEY_ID="${AWS_ACCESS_KEY_ID}"
export AWS_SECRET_ACCESS_KEY="${AWS_SECRET_ACCESS_KEY}"

sudo apt update
sudo apt install ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --batch --yes --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt -y install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# AWS CLI 설치
sudo apt install -y awscli

##2. 도커 이미지 가져오기
aws ecr get-login-password --region ap-northeast-2 | sudo docker login --username AWS --password-stdin 471112990651.dkr.ecr.ap-northeast-2.amazonaws.com
sudo docker pull 471112990651.dkr.ecr.ap-northeast-2.amazonaws.com/algosolved-ecr:main

##3. 도커 실행시키는 명령어
sudo docker run -ti 471112990651.dkr.ecr.ap-northeast-2.amazonaws.com/algosolved-ecr:main


