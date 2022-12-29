# libraryms
图书管理系统

基于spirngboot+mybatisplus+h2+redis（Maven）构建

## 项目简介
本图书管理系统基于springboot开发，数据库为H2，暂无前端界面

## 系统模块
系统分为用户权限控制、图书管理、借阅归还管理模块、超时借阅定时检查模块

## 使用教程

```
1. git clone https://github.com/RAHSFBW/libraryms.git

2. 打开项目

3. 修改application.yml 配置： redis修改成自己本地的redis

4. 项目启动

5. 打开 swagger: http://localhost:12023/libraryms/swagger-ui.html
   打开 h2 : http://localhost:12023/libraryms/h2
        setting 选择 Generic H2 (Embedded)
        jdbcurl: jdbc:h2:file:./dbh2/librarymsdb
        username: root
        password: 123456
6. 默认创建了两个用户和三本书

用户：1. 用户名：admin 密码：12345678 权限: admin（管理员权限）
      2. 用户名：user001 密码：12345678 权限：user(默认权限)
三本书：见数据库book表

7.定时器：项目启动会执行一次，此外每天凌晨1点检查超时借阅记录

```
