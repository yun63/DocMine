## centos 7.6 安装 Mysql 5.7

#### 1 添加Mysql5.7仓库

```bash
sudo rpm -ivh https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
```

#### 2 确认Mysql仓库成功添加

```bash
sudo yum repolist all | grep mysql | grep enabled
```

如果展示像下面,则表示成功添加仓库:

```bash
mysql-connectors-community/x86_64  MySQL Connectors Community    enabled:     51
mysql-tools-community/x86_64       MySQL Tools Community         enabled:     63
mysql57-community/x86_64           MySQL 5.7 Community Server    enabled:    267
```

#### 3 安装

```bash
sudo yum -y install mysql-community-server
```

#### 4 启动

```bash
sudo systemctl start mysqld
sudo systemctl enable mysqld
sudo systemctl status mysqld
```

#### 5 安全设置

CentOS上的root默认密码可以在文件/var/log/mysqld.log找到，通过下面命令可以打印出来

```bash
cat /var/log/mysqld.log | grep -i 'temporary password'
```

执行下面命令进行安全设置，这个命令会进行设置root密码设置，移除匿名用户，禁止root用户远程连接等

```bash
mysql_secure_installation
```



#### 6 问题

```
The GPG keys listed for the "MySQL 5.7 Community Server" repository are already installed but they are not correct for this package.
```

rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022