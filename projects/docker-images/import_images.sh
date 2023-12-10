#!/bin/bash

# 在此处填写镜像文件的保存目录
IMAGE_DIR="./image_tar"

for IMAGE in `ls $IMAGE_DIR`
do
  echo -e "正在导入镜像 \033[33m$IMAGE\033[0m"
  docker load -i ${IMAGE_DIR}/${IMAGE}
  echo -e "已成功导入镜像 \033[33m$IMAGE\033[0m"
  echo ""
done