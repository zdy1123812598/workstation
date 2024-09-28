#发行版
wsl -l -o

#安装

wsl.exe --install Ubuntu


#其他方式  https://github.com/mishamosher/CentOS-WSL
下载的CentOS7.zip复制到指定路径并解压,运行exe,


wsl -l -v


wsl -d CentOS7                        # 进入CentOS系统

wsl --set-default CentOS7        # 将CentOS设置为默认启动的Linux发行版


#注册版本 wsl -l --all
wsl --setdefault wsl名称
# 比如
wsl --setdefault Ubuntu-20.04



Ubuntu：
sudo vim /etc/init.wsl


#! /bin/sh
/etc/init.d/cron $1
/etc/init.d/ssh $1
/etc/init.d/supervisor $1


Windows:  Startup

Set ws = WScript.CreateObject("WScript.Shell")
ws.run "wsl -d Ubuntu-24.04 -u root /usr/sbin/service ssh --full-restart", vbhide


