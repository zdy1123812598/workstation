#pyenv

##安装
···

使用brew安装pyenv
$ brew install pyenv

ubuntu下依赖安装
其他系统请参考官方wiki：https://github.com/pyenv/pyenv/wiki
sudo apt-get update; sudo apt-get install --no-install-recommends make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev xz-utils tk-dev libxml2-dev libxmlsec1-dev libffi-dev liblzma-dev

注意：非root用户安装时，不要加sudo
curl -L https://github.com/pyenv/pyenv-installer/raw/master/bin/pyenv-installer | bash

修改环境变量
vi ~/.zshrc  or ~/.bashrc
export PATH="~/.pyenv/bin:$PATH"
eval "$(pyenv init -)"
eval "$(pyenv virtualenv-init -)"

激活pyenv
source ~/.zshrc


下载安装python
pyenv install 3.7.4

创建虚拟环境
pyenv virtualenv 3.7.4 TEST

激活虚拟环境
pyenv activate TEST

去除当前需要使用的 virtualenv
pyenv deactivate

删除当前需要使用的 virtualenv
pyenv virtualenv-delete test

卸载虚拟环境
pyenv uninstall TEST

列出当前系统所有的pyenv虚拟环境
pyenv versions

设置默认的python版本
pyenv global 3.7.4  #全局
pyenv local TEST #项目文件夹
pyenv shell 3.7.15 #会话shell

取消设置局部
pyenv local --unset

pip升级
pip install -U pip

https://www.jianshu.com/p/f6e392f4e7ee


pyenv commands	列出所有可用的pyenv命令
pyenv versions  显示当前的Python版本及其来源
pyenv install   使用python-build安装Python版本
pyenv global    设置或显示全局Python版本
pyenv local     设置或显示本地应用程序的特定Python版本
pyenv shell     设置或显示shell特定的Python版本
pyenv uninstall 卸载指定的Python版本
pyenv init      为pyenv配置shell环境
pyenv activate  激活虚拟环境
pyenv deactivate停用虚拟环境
pyenv --help    显示更多帮助
pyenv install --list  显示可用的Python版本

pip freeze > requirements.txt

···