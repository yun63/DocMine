## Mysql远程授权

#### 1 编辑配置文件(my.conf)

```shell
# 绑定到指定 IP 地址
bind-address = 0.0.0.0

# 或者注释掉该行
# bind-address = 127.0.0.1
```

#### 2 重启mysql

#### 3 远程授权

```shell
# 授权远程连接的用户访问权限（假设用户名为 remote_user，密码为 user_password，允许从任何主机连接） 
GRANT ALL PRIVILEGES ON *.* TO 'remote_user'@'%' IDENTIFIED BY 'user_password' WITH GRANT OPTION; 
# 刷新权限 
FLUSH PRIVILEGES;
```