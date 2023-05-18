#!/bin/bash
#定义镜像和容器的名称

#读取变量
name=`cat .env | awk -F "=" 'NR==1{print $2}'  `
version=`cat .env | awk -F "=" 'NR==2{print $2}'  `
echo $name
echo $version

#清理
./clean.sh

#镜像生成
docker build -f Dockerfile -t $name:$version .

#判断是否镜像存在
docker images | grep $name &> /dev/null
#如果存在
if [ $? -eq 0 ]
then
    echo $name" image is existed"
else
    echo $name" image is not existed!!!"
fi

#部署
docker-compose -f docker-compose.yml up -d
#判断是否容器存在
docker ps -a | grep $name &> /dev/null
#如果存在
if [ $? -eq 0 ]
then
    echo $name" is up"
else
    echo $name" is not up!!!"
fi