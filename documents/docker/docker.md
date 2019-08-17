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

####Docker容器内无法通过 HTTP 访问外网
```
sudo su
service docker stop
pkill docker
iptables -t nat -F
ifconfig docker0 down
brctl delbr docker0
service docker start
```

##下载镜像加速
*   /etc/docker/daemon.json

    {
       "hosts": ["tcp://0.0.0.0:2376","unix:///var/run/docker.sock"],
       "registry-mirrors": ["https://hccwwfjl.mirror.aliyuncs.com"]
    }

##IDEA无法链接 方法一
*    mkdir -pv ~/.docker
     cp -v {ca,cert,key}.pem ~/.docker
     export DOCKER_HOST=tcp://$HOST:2376 DOCKER_TLS_VERIFY=1


##mysql基础

####进入mysql容器
*   docker exec -it mysql bash

####连接mysql
*   mysql -uroot -p

####修改配置
*   use mysql;
*   ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'new password';
*   FLUSH PRIVILEGES;

####用户授予权限
*   grant all privileges on *.*  to ‘root’@’%’; 
*   GRANT ALL PRIVILEGES ON *.*  ‘root’@’%’ identified by '123' WITH GRANT OPTION;

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
          版本5.7:
*         docker ps -a;          
          docker pull mysql:5.7          
          docker run --name mysql -e MYSQL_ROOT_PASSWORD=123 -p 3306:3306 -d mysql:5.7

          版本8:
          docker pull mysql
          docker run  -d --name mysqllatest  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123 mysql:latest



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

          集群配置:
          docker pull elasticsearch
          # 运行ES1
          docker run --name es1 -e "ES_JAVA_OPTS=-Xms128m -Xmx128m" -d -p 19200:9200 -p 19300:9300 docker.elastic.co/elasticsearch/elasticsearch-oss:latest
          # 运行ES2
          docker run --name es2 -e "ES_JAVA_OPTS=-Xms128m -Xmx128m" -d -p 29200:9200 -p 29300:9300 docker.elastic.co/elasticsearch/elasticsearch-oss:latest

          测试命令：curl localhost:19200 && curl localhost:29200，注意到响应字段cluster_name，不出意料默认应该都是docker-cluster，然后两个es实例对应的name应该不同(随机分配)。
       
          # 进入es1 ,el2
          docker exec -it es1 bash
          # 编辑config目录下的elasticsearch.yml
          vi config/elasticsearch.yml(https://www.elastic.co/guide/en/elasticsearch/reference/current/settings.html)
        
          # 当前机器公网ip
          network.publish_host: 192.168.99.101
          # 其它节点的位置
          discovery.zen.ping.unicast.hosts: ["192.168.99.102:29300"]

          docker restart es1 && docker restart es2

          查看日志：docker logs -f es1
          节点信息：curl http://localhost:19200/_nodes?pretty
          集群健康：curl http://localhost:19200/_cluster/health
          插件elasticsearch-head



####gitlab

*        docker pull gitlab/gitlab-ce

         docker run -d  -p 443:443 -p 80:80 -p 2222:22 --name gitlab --restart always -v /home/gitlab/config:/etc/gitlab -v /home/gitlab/logs:/var/log/gitlab -v /home/gitlab/data:/var/opt/gitlab gitlab/gitlab-ce
         或
         docker run --detach \
         --hostname localhost \
         --publish 444:443 --publish 8880:80 --publish 2222:22 \
         --name gitlab \
         --restart always \
         gitlab/gitlab-ce:latest

         sudo docker exec -t -i gitlab vim /etc/gitlab/gitlab.rb
         
         添加或修改
         # 配置http协议所使用的访问地址,不加端口号默认为80
         external_url "http://192.168.99.100"

         # 配置ssh协议所使用的访问地址和端口
         gitlab_rails['gitlab_ssh_host'] = '192.168.99.100'
         gitlab_rails['gitlab_shell_ssh_port'] = 2222 # 此端口是run时22端口映射的2222端口

         docker restart gitlab

####h2database
*        docker pull oscarfonts/h2
         docker run -d -p 1521:1521 -p 81:81 -v /path/to/local/data_dir:/opt/h2-data --name=h2database oscarfonts/h2
         默认账号 sa  密码  为空         

         创建数据库
         cd /h2/bin
         java -cp h2*.jar org.h2.tools.Shell -url jdbc:h2:/opt/h2-data/test -user sa -password 123


         http://192.168.99.100:81
         本地:jdbc:h2:/opt/h2-data/test;MODE=MySQL
         远程:jdbc:h2:tcp://192.168.99.100:81/opt/h2-data/test;MODE=MySQL
         sa 123





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
         docker run -p 6379:6379 --name redis -d redis:latest redis-server  或 docker run -p 6379:6379 -v $PWD/data:/data  -d redis:3.2 redis-server --appendonly yes        
         docker inspect redis_s | grep IPAddress
         docker exec -it redis_s redis-cli 或 docker exec -it redis_s redis-cli -h 192.168.1.100 -p 6379 -a your_password //如果有密码 使用 -a参数


####zookeeper  kafka
*        docker pull zookeeper
         docker pull wurstmeister/kafka

         docker run -d --name zookeeper -p 2181:2181 -t zookeeper:latest
         docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_ADVERTISED_HOST_NAME=192.168.99.100 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest

         docker exec -it 你的kafka容器id /bin/bash
         
         或

         第一步 搭建zookeeper环境
         docker pull zookeeper
         docker run -d -v /home/soft/zookeeperhost/zookeeperDataDir:/data -v /home/soft/zookeeperhost/zookeeperDataLogDir:/datalog  -e ZOO_MY_ID=1 -e ZOO_SERVERS='server.1=192.168.99.100:2888:3888'  --net=host --name zookeeper --privileged zookeeper

         第二步 创建kafka环境
         docker pull wurstmeister/kafka
         docker run  -d --name kafka -p 9092:9092  --env KAFKA_ADVERTISED_HOST_NAME=localhost  -e KAFKA_ZOOKEEPER_CONNECT=192.168.99.100:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.99.100:9092  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e KAFKA_HEAP_OPTS="-Xmx256M -Xms128M"  --net=host wurstmeister/kafka 

         第三步 验证kafka是否正确安装
         docker exec -it kafka bash
         cd /opt/kafka_2.11-2.0.0/bin/
         ./kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 8 --topic test
         ./kafka-console-producer.sh --broker-list localhost:9092 --topic test

         执行上诉命令后，另起一个标签页，执行如下命令 创建kafka消费者消费消息：
         docker exec -it kafka bash
         cd /opt/kafka_2.11-2.0.0/bin/
         ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

         第四步 搭建kafka管理平台
         docker pull sheepkiller/kafka-manager
         docker run -it -d --rm  -p 9000:9000 -e ZK_HOSTS="192.168.99.100:2181" --net=host sheepkiller/kafka-manager
         firewall-cmd --add-port=9000/tcp

         http://192.168.99.100:9000

         查看消息主题列表：
         ./kafka-topics.sh --list --zookeeper zookeeper:2181
         查看指定topic信息：
         ./kafka-topics.sh --describe --zookeeper zookeeper:2181 --topic test


####rabbitMq
*        docker search rabbitmq:management
         docker pull rabbitmq:management
         docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
         http://192.168.99.100:15672    guest guest