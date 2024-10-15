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




重装WSL

1. 卸载WSL
Note： 以下命令需使用管理员权限 PowerShell 执行。

Get-AppxPackage -AllUsers | Where-Object { $_.Name -like "*Linux*" }
检查是否包含 MicrosoftCorporationII.WindowsSubsystemForLinux 

2 卸载
Get-AppxPackage MicrosoftCorporationII.WindowsSubsystemForLinux | Remove-AppxPackage

2. 安装WSL
2.1 发行版下载地址：https://github.com/microsoft/WSL/releases
2.2 下载 Microsoft.WSL_2.2.4.0_x64_ARM64.msixbundle 或更新版本。
2.3 安装