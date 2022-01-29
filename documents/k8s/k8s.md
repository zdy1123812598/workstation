#k8s 部署

```

master 192.168.235.143  master   api-server:8080
node 192.168.235.144    node1    tomcat:8080  jenkins
node 192.168.235.145    node2    gitlab:80,8080

```
##所有机器 主机master 从机node

####关闭防火墙
```
systemctl stop firewalld
systemctl disable firewalld
setenforce 0
firewall-cmd --state

```
####安装epel-release源
```
yum -y install epel-release

```

##master主机上安装kubernetes Master
```
yum -y install etcd kubernetes-master

```
####
```
vim /etc/etcd/etcd.conf
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"

vim /etc/kubernetes/apiserver

#

# The address on the local server to listen to.
KUBE_API_ADDRESS="--insecure-bind-address=0.0.0.0"

# The port on the local server to listen on.
KUBE_API_PORT="--port=8080"

# Port minions listen on
KUBELET_PORT="--kubelet-port=10250"

# Comma separated list of nodes in the etcd cluster
KUBE_ETCD_SERVERS="--etcd-servers=http://127.0.0.1:2379"

# Address range to use for services
KUBE_SERVICE_ADDRESSES="--service-cluster-ip-range=10.254.0.0/16"

# default admission control policies
KUBE_ADMISSION_CONTROL="--admission-control=NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ResourceQuota"

# Add your own!
KUBE_API_ARGS=""

```

####启动etcd、kube-apiserver、kube-controller-manager、kube-scheduler等服务，并设置开机启动

```

for SERVICES in etcd kube-apiserver kube-controller-manager kube-scheduler; do systemctl restart $SERVICES;systemctl enable $SERVICES;systemctl status $SERVICES ; done

```

####在etcd中定义flannel网络
```
etcdctl mk /atomic.io/network/config '{"Network":"172.17.0.0/16"}'
```

##从机node
```
yum -y install flannel kubernetes-node

```
####为flannel网络指定etcd服务
```
vim /etc/sysconfig/flanneld

# Flanneld configuration options  

    # etcd url location.  Point this to the server where etcd runs
    FLANNEL_ETCD_ENDPOINTS="http://192.168.235.143:2379"

    # etcd config key.  This is the configuration key that flannel queries
    # For address range assignment
    FLANNEL_ETCD_PREFIX="/atomic.io/network"

    # Any additional options that you want to pass
    #FLANNEL_OPTIONS=" 


vim /etc/kubernetes/config

KUBE_LOGTOSTDERR="--logtostderr=true"

    # journal message level, 0 is debug
    KUBE_LOG_LEVEL="--v=0"

    # Should this cluster be allowed to run privileged docker containers
    KUBE_ALLOW_PRIV="--allow-privileged=false"

    # How the controller-manager, scheduler, and proxy find the apiserver
    KUBE_MASTER="--master=http://192.168.235.143:8080

vim /etc/kubernetes/kubelet

    KUBELET_ADDRESS="--address=0.0.0.0"

    # The port for the info server to serve on
    KUBELET_PORT="--port=10250"

    # You may leave this blank to use the actual hostname
    KUBELET_HOSTNAME="--hostname-override=192.168.235.144"

    # location of the api-server
    KUBELET_API_SERVER="--api-servers=http://192.168.235.143:8080"

    # pod infrastructure container
    KUBELET_POD_INFRA_CONTAINER="--pod-infra-container-image=registry.access.redhat.com/rhel7/pod-infrastructure:latest"

    # Add your own!
    KUBELET_ARGS=""


```

####node节点机上启动kube-proxy,kubelet,docker,flanneld等服务，并设置开机启动。
```
for SERVICES in kube-proxy kubelet docker flanneld;do systemctl restart $SERVICES;systemctl enable $SERVICES;systemctl status $SERVICES; done

```

####master主机 master主机
```

kubectl get nodes

```


##gitlab
```
wget  https://mirror.tuna.tsinghua.edu.cn/gitlab-ce/yum/el7/gitlab-ce-11.9.11-ce.0.el7.x86_64.rpm --no-check-certificate
ll
rpm -ivh gitlab-ce-11.9.11-ce.0.el7.x86_64.rpm 

vim /etc/gitlab/gitlab.rb

    external_url 'http://192.168.235.145'
    sed -n 1535p /etc/gitlab/gitlab.rb

sed -n 13p /etc/gitlab/gitlab.rb
sed -n 1535p /etc/gitlab/gitlab.rb

gitlab-ctl reconfigure

访问
http://192.168.235.145
root  gitlab_123

```


##安装jenkins

```

mkdir -p /server/tools
cd /server/tools/
rz
ll

wget --no-check-certificate https://mirrors.cnnic.cn/apache/tomcat/tomcat-8/v8.5.75/bin/apache-tomcat-8.5.75.tar.gz

wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm

wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war

安装jdk
rpm -ivh jdk-8u131-linux-x64.rpm
echo $?
java -version
javac

安装 git

yum install git

git --version


安装tomcat
mkdir /app
tar -xf apache-tomcat-8.5.75.tar.gz -C/app
rm -fr /app/apache-tomcat-8.5.75/webapps/*

安装jenkins
cp jenkins.war /app/apache-tomcat-8.5.75/webapps/ROOT.war
ll /app/apache-tomcat-8.5.75/webapps/
rz
ll -a /root
ll -a /root/.jenkins/
/app/apache-tomcat-8.5.75/bin/startup.sh
netstat -lntup |grep 8080

访问jenkins
http://192.168.235.144:8080
cat /root/.jenkins/secrets/initialAdminPassword

admin jenkins_123


构建项目

cd /server/tools
ssh-keygen -t rsa
id_rsa

ll /root/.ssh/
cat /root/.ssh/id_rsa.pub


```