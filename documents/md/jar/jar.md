

####检索文件路径
jar -tvf app.jar | grep application-prod.yml
#9975 Fri Jun 02 16:04:00 CST 2023 BOOT-INF/classes/application-prod.yml

####解压
jar -xvf app.jar BOOT-INF/classes/application-prod.yml

#BOOT-INF/classes/application-prod.yml

####打包替换
jar -uvf app.jar BOOT-INF/classes/application-prod.yml
#如果BOOT-INF/classes/目录下有多个文件需要打入jar包,可以用下面的方法
jar -uvf app.jar  BOOT-INF/classes/