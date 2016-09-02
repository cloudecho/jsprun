# 开发环境
开发时数据库配置: WebRoot/config.properties

# 安装
1、创建MySQL数据库
```
CREATE DATABASE jsprun DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
grant all on jsprun.* to jsprun@'%' identified by 'jsprun';
grant all on jsprun.* to jsprun@'localhost' identified by 'jsprun';
```

2、在线安装
将程序打包为jsprun.war部署至tomcat，访问
```
http://localhost:8080/jsprun/install.jsp
```