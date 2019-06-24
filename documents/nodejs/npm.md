#npm

##安装npm及cnpm(Windows)
```
1.下载安装
Node.js : http://nodejs.cn/
淘宝NPM: https://npm.taobao.org/

2.测试是否安装成功
npm -v

3.NodeJs下建立"node_global"及"node_cache"两个文件夹，输入以下命令改变npm配置
npm config set prefix "...\node_global"
npm config set cache "...\node_cache"

4.系统环境变量添加系统变量NODE_PATH，输入路径

5.命令行输入以下命令安装express
npm install express -g


6.命令行输入node进入编辑模式，输入以下代码测试是否能正常加载模块：
require('express')


7.安装淘宝npm（cnpm）

npm install -g cnpm --registry=https://registry.npm.taobao.org
cnpm -v

8.添加系统变量path的内容
cnpm会被安装到D:\Program Files\nodejs\node_global下，而系统变量path并未包含该路径。在系统变量path下添加该路径即可正常使用cnpm。


9.安装webpack
npm install webpack -g

10.全局安装vue-cli
npm install --global vue-cli


11.创建一个基于webpack模板的新项目
cmd里输入 vue init webpack my-project

12.安装依赖
cd my-project
cnpm install


13.测试环境是否搭建成功
cnpm run dev



```