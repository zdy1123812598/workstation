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

##Ubuntu下Shadowsocks服务器端安装及优化

```
1.安装pip
sudo apt install python3-pip

2.安装Shadowsocks
pip3 install https://github.com/shadowsocks/shadowsocks/archive/master.zip

查看Shadowsocks版本
sudo ssserver --version
3.创建配置文件

创建Shadowsocks配置文件所在文件夹：
sudo mkdir /etc/shadowsocks

创建配置文件：
sudo nano /etc/shadowsocks/config.json

复制粘贴如下内容
{
    "server":"::",
    "server_port":8388,
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"mypassword",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}

4.测试Shadowsocks配置
ifconfig

测试下Shadowsocks
ssserver -c /etc/shadowsocks/config.json

5.配置Systemd管理Shadowsocks

新建Shadowsocks管理文件
sudo nano /etc/systemd/system/shadowsocks-server.service

复制粘贴：
[Unit]
Description=Shadowsocks Server
After=network.target

[Service]
ExecStart=/usr/local/bin/ssserver -c /etc/shadowsocks/config.json
Restart=on-abort

[Install]
WantedBy=multi-user.target

启动Shadowsocks
sudo systemctl start shadowsocks-server

设置开机启动Shadowsocks：
sudo systemctl enable shadowsocks-server


6.优化

升级Linux内核
uname -r
sudo apt update
sudo apt-cache showpkg linux-image
例子  sudo apt install linux-image-4.10.0-22-generic

重启
sudo reboot

删除老的Linux内核
sudo purge-old-kernels

开启BBR

运行lsmod | grep bbr，如果结果中没有tcp_bbr，则先运行：
modprobe tcp_bbr
echo "tcp_bbr" >> /etc/modules-load.d/modules.conf

echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
echo "net.ipv4.tcp_congestion_control=bbr" >> /etc/sysctl.conf

sysctl -p

保存生效

sysctl net.ipv4.tcp_available_congestion_control
sysctl net.ipv4.tcp_congestion_control


优化吞吐量

sudo nano /etc/sysctl.d/local.conf

复制粘贴：
# max open files
fs.file-max = 51200
# max read buffer
net.core.rmem_max = 67108864
# max write buffer
net.core.wmem_max = 67108864
# default read buffer
net.core.rmem_default = 65536
# default write buffer
net.core.wmem_default = 65536
# max processor input queue
net.core.netdev_max_backlog = 4096
# max backlog
net.core.somaxconn = 4096

# resist SYN flood attacks
net.ipv4.tcp_syncookies = 1
# reuse timewait sockets when safe
net.ipv4.tcp_tw_reuse = 1
# turn off fast timewait sockets recycling
net.ipv4.tcp_tw_recycle = 0
# short FIN timeout
net.ipv4.tcp_fin_timeout = 30
# short keepalive time
net.ipv4.tcp_keepalive_time = 1200
# outbound port range
net.ipv4.ip_local_port_range = 10000 65000
# max SYN backlog
net.ipv4.tcp_max_syn_backlog = 4096
# max timewait sockets held by system simultaneously
net.ipv4.tcp_max_tw_buckets = 5000
# turn on TCP Fast Open on both client and server side
net.ipv4.tcp_fastopen = 3
# TCP receive buffer
net.ipv4.tcp_rmem = 4096 87380 67108864
# TCP write buffer
net.ipv4.tcp_wmem = 4096 65536 67108864
# turn on path MTU discovery
net.ipv4.tcp_mtu_probing = 1

net.ipv4.tcp_congestion_control = bbr


运行
sysctl --system


编辑之前的shadowsocks-server.service文件：
sudo nano /etc/systemd/system/shadowsocks-server.service

在ExecStart前插入一行，内容为：
ExecStartPre=/bin/sh -c 'ulimit -n 51200'

即修改后的shadowsocks-server.service内容为：
[Unit]
Description=Shadowsocks Server
After=network.target

[Service]
ExecStartPre=/bin/sh -c 'ulimit -n 51200'
ExecStart=/usr/local/bin/ssserver -c /etc/shadowsocks/config.json
Restart=on-abort

[Install]
WantedBy=multi-user.target


重载shadowsocks-server.service：
sudo systemctl daemon-reload

重启Shadowsocks
sudo systemctl restart shadowsocks-server

开启TCP Fast Open


编辑config.json
sudo nano /etc/shadowsocks/config.json

重启Shadowsocks
sudo systemctl restart shadowsocks-server

```


##Ubuntu18.04更换国内源

```
备份/etc/apt/sources.list文件
1  mv /etc/apt/sources.list /etc/apt/sourses.list.backup



新建/etc/apt/sources.list文件并添加以下内容
#163源
deb http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse


更改完成之后执行以下命令
# apt update
# apt upgrade


其他的一些apt命令
sudo apt-get update  更新源
sudo apt-get install package 安装包
sudo apt-get remove package 删除包
sudo apt-cache search package 搜索软件包
sudo apt-cache show package  获取包的相关信息，如说明、大小、版本等
sudo apt-get install package --reinstall  重新安装包
sudo apt-get -f install  修复安装
sudo apt-get remove package --purge 删除包，包括配置文件等
sudo apt-get build-dep package 安装相关的编译环境
sudo apt-get upgrade 更新已安装的包
sudo apt-get dist-upgrade 升级系统
sudo apt-cache depends package 了解使用该包依赖那些包
sudo apt-cache rdepends package 查看该包被哪些包依赖
sudo apt-get source package  下载该包的源代码
sudo apt-get clean && sudo apt-get autoclean 清理无用的包
sudo apt-get check 检查是否有损坏的依赖

其他几个国内的源：
#中科大源
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse

#阿里云源
deb http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse

#清华源
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse


#Ubuntu18 启用root用户

1.为root账户设置密码
sudo passwd root

2.进入root账户
su root

3.编辑sshd_config文件
$ vi /etc/ssh/sshd_config
做如下修改：

1.允许root账户登录
PermitRootLogin without-password 
~修改为~
PermitRootLogin yes

2.允许密码登录
PasswordAuthentication no
~修改为~
PasswordAuthentication yes

3.关闭证书验证登录(::确认可以使用密码登录后再做此修改，避免发生无法登录服务器的验证后果::)
UsePAM yes
~修改为~
UsePAM no

4.重启sshd服务
sudo service ssh restart

```