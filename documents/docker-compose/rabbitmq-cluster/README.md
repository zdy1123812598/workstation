# rabbitmq cluster配置部署（普通集群）
- 拉取仓库
```
git clone https://github.com/anyangdp/rabbitmq-cluster.git
```
- 集群配置统一cookie
```
chmod -R 400 .erlang.cookie
```
- 启动服务

```
前台启动：docker-compose up
后台启动：docker-compose up -d
```
- 访问web ui
http://ip:15672
- 客户端连接
ip:5672
- 默认账号密码：guest guest

# 经典镜像模式配置
https://www.jianshu.com/p/c5c1af375916
