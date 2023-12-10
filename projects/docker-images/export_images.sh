#!/bin/bash

# 读取本地 "image:tag" 名称
IMG_NAME=`docker images | grep -v TAG | awk '{print $1":"$2}'`
# echo $IMG_NAME | awk '{gsub(/ /,"\n",$0)} {print $0}'
# 定义镜像存放目录
DIR="./image_tar"
if [ ! -d "$DIR" ]; then
  echo -e "\033[34m${DIR}\033[0m 不存在"
  mkdir -p "$DIR"
  echo -e "\033[34m${DIR}\033[0m 已创建"
else
  echo -e "\033[34m${DIR}\033[0m 已存在"
fi
echo ""
for IMAGE in $IMG_NAME
do
  echo -e "正在保存 \033[33m${IMAGE}\033[0m"
  SAVE_NAME=`echo $IMAGE | awk -F: '{print $1"_"$2}' | sed 's/\//_/g'`
  docker save $IMAGE -o ${DIR}/${SAVE_NAME}.tar
  echo -e "已保存到 \033[34m${DIR}/\033[31m${SAVE_NAME}.tar\033[0m"
  echo ""
done