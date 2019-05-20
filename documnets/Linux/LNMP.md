#LNMP

##LNMP一键安装
```
系统需求:

CentOS/RHEL/Fedora/Debian/Ubuntu/Raspbian/Deepin/Aliyun/Amazon/Mint Linux发行版
需要5GB以上硬盘剩余空间，MySQL 5.7,MariaDB 10至少9GB剩余空间
需要128MB以上内存(128MB小内存VPS,Xen需有SWAP,OpenVZ至少要有128MB以上的vSWAP或突发内存)，注意小内存请勿使用64位系统！
安装MySQL 5.6或5.7及MariaDB 10必须1G以上内存，更高版本至少要2G内存!。
安装PHP 7及以上版本必须1G以上内存!。
VPS或服务器必须已经联网且必须设置的是网络源不能是光盘源，同时VPS/服务器DNS要正常！
Linux下区分大小写，输入命令时请注意！
如有通过yum或apt-get安装的MySQL/MariaDB请自行备份数据等相关文件！
CentOS 5,Debian 6及之前版本其官网已经结束支持无法使用！
Ubuntu 18+,Debian 9+,Mint 19+,Deepin 15.7+及所有新的Linux发行版只能使用1.6进行安装！

LNMP一键安装包 V1.5 已经在Vultr、遨游主机、搬瓦工、LocVPS、Linode、vps2ez、DiaHosting、DigitalOcean、KVMLA、景文互联、RamNode、BuyVM、快易互联、80VPS、阿里云等众多VPS的CentOS 6-7、RHEL 6-7、Fedora 21-28、Debian 7-9、Ubuntu 10.04-18.04的32位和64位系统上测试通过(CentOS 5,Debian 6及之前版本其官网已经结束支持无法使用,Debian9上Apache需选2.4.26版本以上。


安装步骤:
1、使用putty或类似的SSH工具登陆VPS或服务器；

登陆后运行：screen -S lnmp
如果提示screen: command not found 命令不存在可以执行：yum install screen 或 apt-get install screen安装，详细内容参考screen教程。

2、下载并安装LNMP一键安装包：

您可以选择使用下载版(推荐美国及海外VPS或空间较小用户使用)或者完整版(推荐国内VPS使用，国内用户可用在下载中找国内下载地址替换)，两者没什么区别，只是完整版把一些需要的源码文件预先放到安装包里。

安装LNMP稳定版
如需无人值守安装，请使用 无人值守命令生成工具，或查看无人值守说明教程

wget http://soft.vpser.net/lnmp/lnmp1.5.tar.gz -cO lnmp1.5.tar.gz && tar zxf lnmp1.5.tar.gz && cd lnmp1.5 && ./install.sh lnmp

如需要安装LNMPA或LAMP，将./install.sh 后面的参数lnmp替换为lnmpa或lamp即可。如需更改网站和数据库目录、自定义Nginx参数、PHP参数模块、开启lua等需在运行./install.sh 命令前修改安装包目录下的 lnmp.conf 文件，详细可以查看lnmp.conf文件参数说明。

如提示wget: command not found ，使用yum install wget 或 apt-get install wget 命令安装。

如下载速度慢或无法下载请更换其他下载节点，请查看LNMP下载节点具体替换方法。

运行上述LNMP安装命令后，会出现

MYSQ版本列表

目前提供了较多的MySQL、MariaDB版本和不安装数据库的选项，需要注意的是MySQL 5.6,5.7及MariaDB 10必须在1G以上内存的更高配置上才能选择！如仅需安装数据库在lnmp安装包目录下执行：./install.sh db

输入对应MySQL或MariaDB版本前面的序号，回车进入下一步.


设置MySQL的root密码（为了安全不输入直接回车将会设置为lnmp.org#随机数字）如果输入有错误需要删除时，可以按住Ctrl再按Backspace键进行删除(个别情况下是只需要Backspace键)。输入后回车进入下一步

输入密码


询问是否需要启用MySQL InnoDB，InnoDB引擎默认为开启，一般建议开启，直接回车或输入 y ，如果确定确实不需要该引擎可以输入 n，(MySQL 5.7+版本无法关闭InnoDB),输入完成，回车进入下一步。


注意：选择PHP 7+版本时需要自行确认PHP版本是否与自己的程序兼容。

输入要选择的PHP版本的序号，回车进入下一步，选择是否安装内存优化：

可以选择不安装、Jemalloc或TCmalloc，输入对应序号回车，直接回车为默认为不安装。

如果是LNMPA或LAMP的话还会提示设置邮箱和选择Apache“Please enter Administrator Email Address:”，需要设置管理员邮箱，该邮箱会在报错时显示在错误页面上。

再选择Apache版本

按提示输入对应版本前面的数字序号，回车。

提示"Press any key to install...or Press Ctrl+c to cancel"后，按回车键确认开始安装。 
LNMP脚本就会自动安装编译Nginx、MySQL、PHP、phpMyAdmin等软件及相关的组件。

安装时间可能会几十分钟到几个小时不等，主要是机器的配置网速等原因会造成影响。

3、安装完成
如果显示Nginx: OK，MySQL: OK，PHP: OK

并且Nginx、MySQL、PHP都是running，80和3306端口都存在，并提示安装使用的时间及Install lnmp V1.5 completed! enjoy it.的话，说明已经安装成功。
某些系统可能会一直卡在Install lnmp V1.5 completed! enjoy it.不自动退出，可以按Ctrl+c退出。

安装完成接下来开始使用就可以了，按添加虚拟主机教程，添加虚拟主机后可以使用sftp或ftp服务器上传网站代码，将域名解析到VPS或服务器的IP上，解析生效即可使用。

4、安装失败
Error
如果出现类似上图的提示，有一个或几个没安装成功表明安装失败！！需要用winscp或其他类似工具，将/root目录下面的lnmp-install.log下载下来，到LNMP支持论坛发帖注明你的系统发行版名称及版本号、32位还是64位等信息，并将lnmp-install.log压缩以附件形式上传到论坛，我们会通过日志查找错误，并给予相应的解决方法。



默认LNMP是不安装FTP服务器的，如需要FTP服务器：https://lnmp.org/faq/ftpserver.html

5、添加、删除虚拟主机及伪静态管理
https://lnmp.org/faq/lnmp-vhost-add-howto.html

6、eAccelerator、xcache、memcached、imageMagick、ionCube、redis、opcache的安装
https://lnmp.org/faq/addons.html

7、LNMP相关软件目录及文件位置
https://lnmp.org/faq/lnmp-software-list.html

8、LNMP状态管理命令
https://lnmp.org/faq/lnmp-status-manager.html

9、仅安装数据库、Nginx
lnmp 1.5开始支持只安装MySQL/MariaDB数据库或Nginx
增加单独nginx安装，安装包目录下运行：./install.sh nginx 进行安装；
增加单独数据库安装，安装包目录下运行：./install.sh db 进行安装；

```