#docker学习资料

##docker基础

####从公网拉取一个镜像
*   docker pull images_name

####查看已有的docker镜像
*   docker images

####查看帮助
*   docker command --help

####查看镜像列表
*   docker search nginx

####启动一个容器
*   docker run --name=con_name images
*    --name  #设置容器名

####基于创建好的容器自定义docker镜像
*    docker commit -m "con_name" con_id image_name

####创建一个容器的同时进入这个容器
*    docker run -it --name=con_name images
*    -it     #在启动之后进入这个容器


####创建一个容器，放入后台运行，把物理机80端口映射到容器的80端口
*    docker run -d -p 81:80 image_name
*    -p 参数说明
*    -p hostPort:containerPort
*    -p ip:hostPort:containerPort
*    -p ip::containerPort
*    -p hostPort:containerPort:udp

####查看看容器的端口映射情况
*    docker port con_id

####查看正在运行的容器
*    docker ps

####查看所有的容器
*    docker ps -a

####动态查看容器日志
*    docker logs -f con_name

####进入容器
*    docker attach con_name

####查看容器所有状态信息
*    docker inspect NAMES 

####查看容器ip地址
*    docker inspect --format='{{.NetworkSettings.IPAddress}}' ID/NAMES

####容器运行状态
*    docker inspect --format '{{.Name}} {{.State.Running}}' NAMES

####boot2docker用户和密码
*    docker	tcuser	ssh
*    root　　　command：sudo -i (docker用户下执行)

####启动容器
*    docker start 容器ID或容器名

####进入容器
*    1　docker run -itd ubuntu:14.04 /bin/bash 
*    2　docker attach 44fc0f0582d9
*    3　进入容器内部后 cat /etc/hosts
*    4　docker inspect  
　　　　docker inspect --format '{{ .NetworkSettings.IPAddress }}' <container-ID> 或 <br/>
　　　　docker inspect <container id> 或 <br/>
　　　　docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id

####~/.bashrc 中写一个 bash 函数：
*       function docker_ip() {
            sudo docker inspect --format '{{ .NetworkSettings.IPAddress }}' $1
        }
*    source ~/.bashrc  然后
*    docker_ip <container-ID>

####获取所有容器名称及其IP地址只需一个命令
*    docker inspect -f '{{.Name}} - {{.NetworkSettings.IPAddress }}' $(docker ps -aq)
*    使用docker-compose命令: docker inspect -f '{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -aq)

####显示所有容器IP地址
*    docker inspect --format='{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -aq)

####查看mysql容器的ip
*    docker inspect --format '{{ .NetworkSettings.IPAddress }}' <container-ID> 

####获取docker主机 IP
*    docker-machine ip

####删除container其中的images
*    停止container  
*    docker stop $(docker ps -a -q)

####查看所有images
*    docker images

####删除images
*    docker rmi <image id>

####删除untagged images
*    docker rmi $(docker images | grep "^<none>" | awk "{print $3}")   #id为<None>的image

####删除全部image
*    docker rmi $(docker images -q)

####查看本地文件系统的磁盘
*    df -h

####查看Docker的磁盘使用情况
*    docker system df

####清理磁盘，删除关闭的容器、无用的数据卷和网络，以及dangling镜像(即无tag的镜像)
*    docker system prune
####删掉所有没有容器使用的Docker镜像，清理得更加彻底
*    docker system prune -a

####手动清理Docker镜像/容器/数据卷
```
停止所有运行的容器
# docker stop $(docker ps -a | awk '{print $1}')   
删除所有关闭的容器
# docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs docker rm
删除所有dangling镜像(即无tag的镜像)：
# docker rmi $(docker images | awk "{print $2}" | grep "^<none>")
删除所有dangling数据卷(即无用的volume)：
# docker volume rm $(docker volume ls -qf dangling=true)
查看清理之后的磁盘状态
# df -h
```



##mysql基础

####进入mysql容器
*   docker exec -it ly-mysql bash

####连接mysql
*   mysql -uroot -p

####修改配置
*   use mysql;
*   ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'new password';
*   FLUSH PRIVILEGES;

####用户授予权限
*   grant all privileges on *.*  to ‘root’@’%’; 
*   GRANT ALL PRIVILEGES ON *.*  ‘root’@’%’ identified by '' WITH GRANT OPTION;

####刷新权限
*   flush privileges;

####修改用户的密码和加密方式
*         ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '密码';
          ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123';
          ALTER user 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456;
          flush privileges;
          alter user 'root'@'%' identified with mysql_native_password by '123';   

*         mysql -uroot -p
          mysql> use mysql;
          mysql> alter user 'root'@'%' identified with mysql_native_password by '123';   
          
          update user set host='%' where user='root';
          mysql> flush privileges;
          mysql> select host,user,plugin,authentication_string from mysql.user;
          update user set host='%' where user='root';

####创建mysql
*         docker ps -a;          
          docker pull mysql:5.7          
          docker run --name zdy-mysql -e MYSQL_ROOT_PASSWORD=123 -p 3306:3306 -d mysql:5.7


####查看mysql容器的ip
*   docker inspect --format '{{ .NetworkSettings.IPAddress }}' <container-ID> 

####Java配置
*         export JAVA_HOME=/usr/java/jdk1.8.0_201
          export JRE_HOME=${JAVA_HOME}/jre
          export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:$CLASSPATH
          export JAVA_PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin
          export PATH=$PATH:${JAVA_PATH}
          
####mongo创建
*         docker search mongo          
          docker pull mongo          
          docker images mongo          
          docker run -d --name=mongo -p 27017:27017 -e AUTH=no mongo          
          docker run -p 27017:27017 -td mongo
          docker ps
          docker exec -it 镜像id /bin/bash
          
          use admin
          db.createUser(
          {
          user: "admin",
          pwd: "admin",
          roles: [ { role: "root", db: "admin" } ]
          }
          );
          
          mongo --port 27017 -u admin -p password --authenticationDatabase admin

####elasticsearch
*         docker pull elasticsearch          
          docker run -d -p 9200:9200 -v ~/elasticsearch/data:/usr/share/elasticsearch/data --name elasticsearch elasticsearch
          
          
          部署Logstash服务  /logstash/logstash.conf 配置文件如下          
          input {
          	syslog {
          		type = >"rsyslog"port = >4560
          	}
          }
          output {
          	elasticsearch {
          		hosts = >["elasticsearch:9200"]
          	}
          }
          
          docker run -d -p 4560:4560  -v ~/logstash/logstash.conf:/etc/logstash.conf  --link elasticsearch:elasticsearch  --name logstash logstash  logstash -f /etc/logstash.conf
          
          部署Kibana服务
          docker run -d -p 5601:5601  --link elasticsearch:elasticsearch  -e ELASTICSEARCH_URL=http://elasticsearch:9200  --name kibana kibana
          
          启动nginx容器来生产日志
          docker run -d -p 90:80 --log-driver syslog --log-opt  syslog-address=tcp://localhost:514 --log-opt tag="nginx" --name nginx nginx
          localhost:90 来打开Nginx界面
          
          打开 Kibana 可视化界面：localhost:5601
          
*         docker pull docker.elastic.co/elasticsearch/elasticsearch:6.3.2
          
          docker run -d --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.3.2
          
          docker exec -it es /bin/bash
          
          # 显示文件
          ls
          结果如下：
          LICENSE.txt  README.textile  config  lib   modules
          NOTICE.txt   bin             data    logs  plugins
          
          # 进入配置文件夹
          cd config
          
          # 显示文件
          ls
          结果如下：
          elasticsearch.keystore  ingest-geoip  log4j2.properties  roles.yml  users_roles
          elasticsearch.yml       jvm.options   role_mapping.yml   users
          
          # 修改配置文件
          vi elasticsearch.yml
          
          # 加入跨域配置
          http.cors.enabled: true
          http.cors.allow-origin: "*"
          
          docker restart es
          
          
          docker pull mobz/elasticsearch-head:5
          docker run -d --name es_admin -p 9100:9100 mobz/elasticsearch-head:5
          
          docker exec -it es_admin /bin/bash





####gitlab

*        docker pull gitlab/gitlab-ce
         
         docker run --detach \
         --hostname localhost \
         --publish 444:443 --publish 8880:80 --publish 2222:22 \
         --name gitlab \
         --restart always \
         gitlab/gitlab-ce:latest

         sudo docker exec -t -i gitlab vim /etc/gitlab/gitlab.rb

         external_url "http://192.168.99.100"



####docker镜像的导出和导入
*   docker images
*   docker save -o <保存路径> <镜像名称:标签>
*       docker save -o ./ubuntu18.tar ubuntu:18.04
*       docker load --input ./ubuntu18.tar

####docker容器的导出与导入
*   docker ps
*   docker stop <容器名>
*       docker stop ubuntu18
*   docker export <容器名> > <保存路径>
*       docker export ubuntu18 > ./ubuntu18.tar
*   docker import <文件路径>  <容器名>
*       docker import ./ubuntu18.tar ubuntu18
*   docker start <容器名>
*       docker start ubuntu18
*   docker exec -it ubuntu18 /bin/bash





####mysql启动报错 No directory, logging in with HOME=/
*    usermod -d /var/lib/mysql/ mysql　　　　　　　　　 #第一步
*    ln -s /var/lib/mysql/mysql.sock /tmp/mysql.sock　　　#第二步
*    chown -R mysql:mysql /var/lib/mysql　　　　　　　　#第三步
*    service mysql restart　　　　　　　　　　　　　　　#之后重启mysql即可

####启动mysql
*    service mysql start;

####mysql状态   
*    service mysql status;


####Redis
*        docker search  redis
         docker pull  redis
         docker run -p 6379:6379 -d redis:latest redis-server  或 docker run -p 6379:6379 -v $PWD/data:/data  -d redis:3.2 redis-server --appendonly yes
        
         docker inspect redis_s | grep IPAddress

         docker exec -it redis_s redis-cli 或 docker exec -it redis_s redis-cli -h 192.168.1.100 -p 6379 -a your_password //如果有密码 使用 -a参数

