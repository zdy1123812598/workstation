FROM centos:7.9

# openrestry 版本
ARG OPENRESTY_VERSION="1.21.4.1"

# 第三方模块版本，我安装 ngx-fancyindex 模块
#ARG FANCYINDEX_VERSION="0.5.2"

# 镜像信息
LABEL AUTHOR=zdy

# 如果事先下载第三模块的压缩包，则放入 Dockerfile 所在的目录下，取消注释
#ADD ngx-fancyindex-${FANCYINDEX_VERSION}.tar.xz /tmp

# 安装 Nginx 环境库，下载第三方模块和 Nginx 或 Openresty，并解压编译安装
RUN yum install -y \
     gcc pcre pcre-devel zlib zlib-devel openssl openssl-devel --nogpgcheck \
     curl wget perl make vim tar \
    && cd /tmp \ 
    && wget https://openresty.org/download/openresty-${OPENRESTY_VERSION}.tar.gz \
    # 如果不喜欢先下载第三方模块然后 ADD，则这里填写第三方模块的下载地址，然后取消下面两行注释
    # && wget https://github.com/aperezdc/ngx-fancyindex/releases/download/v${FANCYINDEX_VERSION}/ngx-fancyindex-${FANCYINDEX_VERSION}.tar.xz \
    # && tar xvf ngx-fancyindex-${FANCYINDEX_VERSION}.tar.xz \
    && tar zxvf openresty-${OPENRESTY_VERSION}.tar.gz \
    && cd openresty-${OPENRESTY_VERSION} \
    && ./configure \
        --prefix=/usr/local/openresty \
        --with-cc-opt='-O2 -DNGX_LUA_ABORT_AT_PANIC -I/usr/local/openresty/zlib/include -I/usr/local/openresty/pcre/include -I/usr/local/openresty/openssl111/include' \
        --with-ld-opt='-Wl,-rpath,/usr/local/openresty/luajit/lib -L/usr/local/openresty/zlib/lib -L/usr/local/openresty/pcre/lib -L/usr/local/openresty/openssl111/lib -Wl,-rpath,/usr/local/openresty/zlib/lib:/usr/local/openresty/pcre/lib:/usr/local/openresty/openssl111/lib' \
        --with-pcre-jit \
        --with-stream \
        --with-stream_ssl_module \
        --with-stream_ssl_preread_module \
        --with-http_v2_module \
        --without-mail_pop3_module \
        --without-mail_imap_module \
        --without-mail_smtp_module \
        --with-http_stub_status_module \
        --with-http_realip_module \
        --with-http_addition_module \
        --with-http_auth_request_module \
        --with-http_secure_link_module \
        --with-http_random_index_module \
        --with-http_gzip_static_module \
        --with-http_sub_module \
        --with-http_dav_module \
        --with-http_flv_module \
        --with-http_mp4_module \
        --with-http_gunzip_module \
        --with-threads --with-stream \
        --with-http_ssl_module \
        # --add-module=../ngx-fancyindex-${FANCYINDEX_VERSION} \
    && make build \
    && make install \
    && cd /tmp \
    && rm -rf ngx-fancyindex-${FANCYINDEX_VERSION}.tar.xz \
    && rm -rf openresty-${OPENRESTY_VERSION}.tar.gz \
    && yum clean all \
    && mkdir -p /etc/nginx/conf.d \
    # 必须创建一个目录，如果这个目录不存在，则无法启动镜像
    && mkdir -p /var/run/openresty

# 将 nginx 命令添加至全局变量，这里演示常用的三个
#ENV PATH=$PATH:/usr/local/openresty/luajit/bin:/usr/local/openresty/nginx/sbin:/usr/local/openresty/bin
ENV LUA_PATH="/usr/local/openresty/site/lualib/?.ljbc;/usr/local/openresty/site/lualib/?/init.ljbc;/usr/local/openresty/lualib/?.ljbc;/usr/local/openresty/lualib/?/init.ljbc;/usr/local/openresty/site/lualib/?.lua;/usr/local/openresty/site/lualib/?/init.lua;/usr/local/openresty/lualib/?.lua;/usr/local/openresty/lualib/?/init.lua;./?.lua;/usr/local/openresty/luajit/share/luajit-2.1.0-beta3/?.lua;/usr/local/share/lua/5.1/?.lua;/usr/local/share/lua/5.1/?/init.lua;/usr/local/openresty/luajit/share/lua/5.1/?.lua;/usr/local/openresty/luajit/share/lua/5.1/?/init.lua"
#ENV LUA_CPATH="/usr/local/openresty/site/lualib/?.so;/usr/local/openresty/lualib/?.so;./?.so;/usr/local/lib/lua/5.1/?.so;/usr/local/openresty/luajit/lib/lua/5.1/?.so;/usr/local/lib/lua/5.1/loadall.so;/usr/local/openresty/luajit/lib/lua/5.1/?.so"

# 将 nginx 配置文件，覆盖 Nginx 自带的配置文件
COPY nginx.conf /usr/local/openresty/nginx/conf/nginx.conf

# 将 default.conf 配置文件，添加到指定目录下
COPY nginx.vh.default.conf /etc/nginx/conf.d/default.conf

# 启动容器时，执行该命令，如果不执行，则容器启动后会自动停止运行
CMD ["/usr/local/openresty/nginx/sbin/nginx", "-g", "daemon off;"]

# Use SIGQUIT instead of default SIGTERM to cleanly drain requests
# See https://github.com/openresty/docker-openresty/blob/master/README.md#tips--pitfalls
STOPSIGNAL SIGQUIT
