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


