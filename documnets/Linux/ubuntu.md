#ubuntu


##ubuntu下apt-get安装和彻底卸载mysql

```
1、安装mysql：
sudo apt-get install mysql-server
sudo apt-get install mysql-client
sudo apt-get install php5-mysql(用于连接php和mysql)

查看mysql是否运行
ps aux | grep mysql

启动命令
/etc/init.d/mysql start

2.删除mysql
按顺序执行以下命令
sudo apt-get autoremove --purge mysql-server-5.0
sudo apt-get remove mysql-server
sudo apt-get autoremove mysql-server
sudo apt-get remove mysql-common 

清理残留数据
dpkg -l |grep ^rc|awk '{print $2}' |sudo xargs dpkg -P
```