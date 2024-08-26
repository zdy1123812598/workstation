#anaconda


```
下载 
官网地址：https://www.anaconda.com/products/distribution
官网历史版本下载网址：https://repo.anaconda.com/archive/


安装
把如下路径添加到环境变量中
..\Anaconda
..\Anaconda\Scripts

conda --version

删除之前的镜像源，恢复默认状态：conda config --remove-key channels

添加镜像源
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/conda-forge/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/pytorch/
 
添加阿里云镜像源
conda config --add channels https://mirrors.aliyun.com/anaconda/pkgs/free/
conda config --add channels https://mirrors.aliyun.com/anaconda/pkgs/main/

中国科学技术大学
conda config --add channels https://mirrors.ustc.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.ustc.edu.cn/anaconda/pkgs/main/

显示检索路径
conda config --set show_channel_urls yes
# 保证换源之后优先级是正确的
conda config --set channel_priority true
显示镜像通道
conda config --show channels


删除下载源
使用 --remove 命令

conda config --remove channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/



Windows系统：在 C:\user\username\目录下，创建 pip 文件夹，并在该文件夹内创建 pip.ini 文件
即在 C:\user\username\pip\pip.ini 中，加入以下内容：

[global]
index-url=https://pypi.tuna.tsinghua.edu.cn/simple
[install]
trusted-host=pypi.tuna.tsinghua.edu.cn
disable-pip-version-check = true
timeout = 6000


下载目录
一般对一个install命令 执行两次，例如：pip install numpy，在第二次即可展示安装的位置

使用命令：python -m site

修改下载目录
通过命令：python -m site -help

修改虚拟环境地址
通过配置文件 .condarc



通过命令指定
指定位置新建虚拟环境
conda create --prefix=/home/conda_env/mmcv python=3.7
注意：路径 /home/conda_env 是自己先建立好的，也就是必须存在这个路径，后面的 mmcv 就是你想给这个环境取的一个名称

激活这个环境
source activate /home/conda_env/mmcv
注意：Linux下是source，Windows下是 conda

退出该虚拟环境：conda deactivate

删除该虚拟环境
conda remove --prefix=/home/conda_env/mmcv --all


当前的环境中终端中使用 conda env export > environment.yaml 将你当前的环境保存到文件中包保存为YAML文件（包括Pyhton版本和所有包的名称）。

以下命令更新你的环境：
conda env update -f=/path/to/environment.yml
其中-f表示你要导出文件在本地的路径，所以/path/to/environment.yml要换成你本地的实际路径


查看所有环境：
conda env list

创建一个名为 "myenv" 的新环境:
conda create --name myenv

创建指定版本的环境：
conda create --name myenv python=3.8

激活环境：
conda activate myenv

要退出当前环境使用以下命令：
deactivate

复制环境：
conda create --name myclone --clone myenv

删除环境：
conda env remove --name myenv

查看已安装的包：
conda list

安装包：
conda install package_name

安装指定版本的包：
conda install package_name=1.2.3

更新包：
conda update package_name

卸载包：
conda remove package_name

查看 conda 版本：
conda --version

搜索包：
conda search package_name

清理不再需要的包：
conda clean --all

安装 Jupyter Notebook：
conda install jupyter

启动 Jupyter Notebook：
jupyter notebook


查看是否存在.condarc文件：
conda config --show-sources

删除.condarc文件
conda clean -i



```