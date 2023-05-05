```
1，查看是否安装openssl
    openssl version -a

2，没有安装执行
    yum install mod_ssl openssl

3，在nginx目录下创建ssl文件夹
    cd /etc/nginx/
    mkdir ssl
    cd ssl

4,生成2048位的加密私钥
    openssl genrsa -out server.key 2048

5,生成证书签名请求（CSR），这里需要填写许多信息
    openssl req -new -key server.key -out server.csr

    输出内容为：
    Enter pass phrase for root.key: ← 输入前面创建的密码
    Country Name (2 letter code) [AU]:CN ← 国家代号，中国输入CN
    State or Province Name (full name) [Some-State]:BeiJing ← 省的全名，拼音
    Locality Name (eg, city) []:BeiJing ← 市的全名，拼音
    Organization Name (eg, company) [Internet Widgits Pty Ltd]:MyCompany Corp. ← 公司英文名
    Organizational Unit Name (eg, section) []: ← 可以不输入
    Common Name (eg, YOUR name) []: ← 服务器主机名，若填写不正确，浏览器会报告证书无效，但并
    Email Address []:admin@mycompany.com ← 电子邮箱，可随意填
    Please enter the following ‘extra’ attributes
    to be sent with your certificate request
    A challenge password []: ← 可以不输入
    An optional company name []: ← 可以不输入

6，生成类型为X509的自签名证书。有效期设置3650天，即有效期为10年

    openssl x509 -req -days 3650 -in server.csr -signkey server.key -out server.crt

7，查看当前ssl目录
    ls

8,将需要使用的域名添加至本地电脑host文件，ip为虚拟机ip地址
    127.0.0.1 cim.cbim.com


9，查看本地nginx配置文件
    find / -name nginx.conf
    
    打开文件进行编辑 vim /usr/local/nginx/conf/nginx.conf

        ssl_certificate      /usr/local/nginx/ssl/server.crt;
        ssl_certificate_key  /usr/local/nginx/ssl/server.key;

        ssl_session_cache    shared:SSL:1m;
        ssl_session_timeout  5m;

        ssl_ciphers  HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers  on;


```