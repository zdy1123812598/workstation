#SSH 公钥

###windows下生成ssh 公钥

```
1. 安装git，从程序目录打开 "Git Bash" ,或者直接用git shell，github自带的工具
2. 键入命令：ssh-keygen -t rsa -C "email@email.com"
  "email@email.com"是github账号
3. 提醒你输入key的名称，你可以不用输入，直接3个回车，就OK了；
4. 在C:\Documents and Settings\Administrator\下产生两个文件：id_rsa和id_rsa.pub
5. 把4中生成的密钥文件复制到C:\Documents and Settings\Administrator\.ssh\ 目 录下。
6. 用记事本打开id_rsa.pub文件，复制内容，在coding.net的网站上到ssh密钥管理页面，添加新公钥

```

###Linux下生成ssh 公钥

```
生成 SSH 公钥
SSH 公钥默认储存在账户的主目录下的 ~/.ssh 目录

cd ~/.ssh
ls
ssh-keygen
cat ~/.ssh/id_rsa.pub
```