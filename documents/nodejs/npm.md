#npm

##安装npm及cnpm(Windows)
```
1.下载安装
Node.js : http://nodejs.cn/
淘宝NPM: https://npm.taobao.org/

2.测试是否安装成功
npm -v

3.NodeJs下建立"node_global"及"node_cache"两个文件夹，输入以下命令改变npm配置
sudo npm config set prefix "...\node_global"
sudo npm config set cache "...\node_cache"

4.系统环境变量添加系统变量NODE_PATH，输入路径

5.命令行输入以下命令安装express
sudo npm install express -g


6.命令行输入node进入编辑模式，输入以下代码测试是否能正常加载模块：
require('express')


7.安装淘宝npm（cnpm）

npm install -g cnpm --registry=https://registry.npm.taobao.org
cnpm -v

8.添加系统变量path的内容
cnpm会被安装到D:\Program Files\nodejs\node_global下，而系统变量path并未包含该路径。在系统变量path下添加该路径即可正常使用cnpm。


9.安装webpack
sudo npm install webpack -g

10.全局安装vue-cli
sudo npm install --global vue-cli


11.创建一个基于webpack模板的新项目
cmd里输入 vue init webpack my-project

12.安装依赖
cd my-project
cnpm install


13.测试环境是否搭建成功
cnpm run dev

14.eslint全局安装
npm install  eslint # 安装到命令行目前所在目录下 
sudo npm install -g eslint # -g 是指安装到全局目录，可以通过 npm config set prefix 来设置"目录路径"    的意思是将模块安装到全局，具体安装到磁盘哪个位置，要看 npm config prefix 的位置。 
npm install -save eslint  # -save 是指将包安装到当前项目的node_modules文件夹下下，并在package文件的dependencies节点写入依赖。 
npm install -save-dev eslint # -save-dev 是指将包安装在当前项目的node_modules文件夹下，并将安装包信息写入package.json文件的devDependencies字段中


15.yarn安装
sudo npm install -g yarn


16.设置npm的registry

1.临时使用
npm --registry https://registry.npm.taobao.org install express

2.持久使用
npm config set registry https://registry.npm.taobao.org
配置后可通过下面方式来验证是否成功 
npm config get registry 或  npm info express

3.通过cnpm使用
npm install -g cnpm --registry=https://registry.npm.taobao.org


17.npm升级
npm install npm --no-optional

```

##创建vue工程
```
1.切换到需要创建的目录 vue init webpack vue_project
2.回车 Y

```