#!/bin/bash
#定义镜像和容器的名称

#读取变量
name=`cat .env | awk -F "=" 'NR==1{print $2}'  `
version=`cat .env | awk -F "=" 'NR==2{print $2}'  `
echo $name
echo $version


#判断是否容器存在
docker ps -a | grep $name &> /dev/null
#如果存在，关闭并删除该容器
if [ $? -eq 0 ]
then
    echo $name" is up,we will stop and remove it!!!"
    docker stop $name 
    docker-compose -f docker-compose.yml down
    docker rm $name 
else
    echo $name" is not up!!!"
fi

#判断是否镜像存在
docker images | grep $name &> /dev/null
#如果存在，删除该镜像
if [ $? -eq 0 ]
then
    echo $name" image is existed,we will remove it!!!"
    docker rmi $(docker images | grep $name | awk "{print $3}")
else
    echo $name" image is not existed!!!"
fi