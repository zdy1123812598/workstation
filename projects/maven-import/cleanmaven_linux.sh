#!/bin/bash

# 设置Maven本地仓库路径
repository_path=~/.m2/repository

# 遍历本地仓库，查找损坏的依赖
find "$repository_path" -type f -name "*.jar" -exec sh -c '
  for jar do
    # 使用mvn命令检查依赖是否损坏
    if ! mvn dependency:analyze -DignoreNonCompile=true -f /dev/null -Dmaven.repo.local="$repository_path" -Dartifact="$jar" >/dev/null 2>&1 ; then
      echo "删除损坏的依赖: $jar"
      rm -f "$jar"
    fi
  done
' sh {} +

echo "删除操作已完成。"

#chmod 777 clean-maven-repo.sh
