### 前提条件：

操作系统中先安装部署Docker Desktop软件
https://www.docker.com/products/docker-desktop

启动 Docker Desktop 软件之后，右下角应该会显示 Docker 图标，并且正在运行

**启动**

根据需要修改配置文件（.env），然后双击 startup.bat 文件启动 iManager

> 请填写环境变量IMANAGER_HOST_IP的值为Docker所在宿主机IP，该项为必填项
>
> 默认使用[Docker Hub](https://hub.docker.com/)的镜像，可通过.env文件中IMANAGER_REGISTRY_URL变量的值指定镜像仓库
>
> 默认版本为latest，可通过.env文件中TAG变量的值指定要部署的版本
>
> 比如宿主机IP为192.168.17.126，并且要使用阿里云镜像仓库以及10.1.0版本,，可以修改配置为：

~~~sh 
IMANAGER_HOST_IP=192.168.17.126
IMANAGER_REGISTRY_URL=registry.cn-beijing.aliyuncs.com
TAG=10.1.0
~~~

启动 iManager 之后，可以通过 http://localhost:8390 访问 iManager 界面。

**停用**

双击 stop.bat 文件停用 iManager

**卸载**

双击 shutdown.bat 文件卸载 iManager






