FROM ubuntu:20.04

# 设置非交互模式，避免 apt 安装时弹出交互提示
ENV DEBIAN_FRONTEND=noninteractive
ENV DISPLAY=:1
# 设置时区（可选）x11vnc
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


# 替换软件源为阿里云镜像
RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    sed -i 's/security.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list

# 更新系统并安装常用工具
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    curl \
    vim \
    net-tools \
    iputils-ping \
    openssh-server \
    && \
    # 清理缓存，减小镜像体积
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

#sshd设置
# 创建 SSH 目录
RUN mkdir /var/run/sshd

# 设置 root 密码（例如：设置密码为 "password"）
RUN echo 'root:root' | chpasswd

# 配置 SSH 允许 root 登录
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
    && sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PermitEmptyPasswords no/PermitEmptyPasswords no/' /etc/ssh/sshd_config
# 暴露 SSH 端口
EXPOSE 22

COPY start.sh /start.sh
RUN chmod +x /start.sh
CMD ["/start.sh"]