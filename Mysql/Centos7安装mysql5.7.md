## CentOS安装MySQL详解

Linux上安装软件常见的几种方式：

* 源码编译
* 压缩包解压（一般为tar.gz）
* 编译好的安装包（RPM、DPKG等）
* 在线安装（YUM、APT等）

以上几种方式便捷性依次增加，但通用性依次下降，比如直接下载压缩包进行解压，这种方式一般需要自己做一些额外的配置工作，但只要掌握了方法，各个平台基本都适用，YUM虽然简单，但是平台受限，网络受限，必要的时候还需要增加一些特定YUM源。

几种安装方式最好都能掌握，原则上能用简单的就用简单的：YUM>RPM>tar.gz>源码

本文是介绍MySQL在CentOS上的安装，主要步骤都是参考了MySQL官方文档：https://dev.mysql.com/doc/refman/8.0/en/

为了测试不同安装方式，反复折腾了好几次，装了删，删了装，每个步骤都是亲测成功的，每条命令都是亲自执行过的，可以放心使用

咱们闲话少说，书归正传（这闲话就不少了...）


### 1. 删除已安装的MySQL
检查MariaDB
```
shell> rpm -qa|grep mariadb
mariadb-server-5.5.60-1.el7_5.x86_64
mariadb-5.5.60-1.el7_5.x86_64
mariadb-libs-5.5.60-1.el7_5.x86_64
```

#### 删除mariadb

如果不存在（上面检查结果返回空）则跳过步骤
```
shell> rpm -e --nodeps mariadb-server
shell> rpm -e --nodeps mariadb
shell> rpm -e --nodeps mariadb-libs
```

其实yum方式安装是可以不用删除mariadb的，安装MySQL会覆盖掉之前已存在的mariadb

#### 检查MySQL
shell> rpm -qa|grep mysql

#### 删除MySQL
如果不存在（上面检查结果返回空）则跳过步骤

shell> rpm -e --nodeps xxx


### 2. 添加MySQL Yum Repository
从CentOS 7开始，MariaDB成为Yum源中默认的数据库安装包。也就是说在CentOS 7及以上的系统中使用yum安装MySQL默认安装的会是MariaDB（MySQL的一个分支）。如果想安装官方MySQL版本，需要使用MySQL提供的Yum源。

下载MySQL源
官网地址：dev.mysql.com/downloads/r…

查看系统版本：
```
shell> cat /etc/redhat-release
CentOS Linux release 7.6.1810 (Core)
```

选择对应的版本进行下载，例如CentOS 7当前在官网查看最新Yum源的下载地址为： dev.mysql.com/get/mysql80…

shell> wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm

安装MySQL源
```
shell> sudo rpm -Uvh platform-and-version-specific-package-name.rpm
```

例如CentOS7当前最新MySQL源安装：

```
shell> sudo rpm -Uvh mysql80-community-release-el7-3.noarch.rpm
```

检查是否安装成功
执行成功后会在/etc/yum.repos.d/目录下生成两个repo文件mysql-community.repo及 mysql-community-source.repo

并且通过yum repolist可以看到mysql相关资源

```
shell> yum repolist enabled | grep "mysql.*-community.*"
mysql-connectors-community/x86_64 MySQL Connectors Community                108
mysql-tools-community/x86_64      MySQL Tools Community                  90
mysql80-community/x86_64          MySQL 8.0 Community Server             113
```

### 3. 选择MySQL版本
使用MySQL Yum Repository安装MySQL，默认会选择当前最新的稳定版本，例如通过上面的MySQL源进行安装的话，默安装会选择MySQL 8.0版本，如果就是想要安装该版本，可以直接跳过此步骤，如果不是，比如我这里希望安装MySQL5.7版本，就需要“切换一下版本”：

查看当前MySQL Yum Repository中所有MySQL版本（每个版本在不同的子仓库中）
shell> yum repolist all | grep mysql

切换版本

```
shell> sudo yum-config-manager --disable mysql80-community
shell> sudo yum-config-manager --enable mysql57-community
```

除了使用yum-config-manager之外，还可以直接编辑/etc/yum.repos.d/mysql-community.repo文件

enabled=0禁用
```
[mysql80-community]
name=MySQL 8.0 Community Server
baseurl=http://repo.mysql.com/yum/mysql-8.0-community/el/7/$basearch/
enabled=0
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
```
enabled=1启用


```
# Enable to use MySQL 5.7
[mysql57-community]
name=MySQL 5.7 Community Server
baseurl=http://repo.mysql.com/yum/mysql-5.7-community/el/7/$basearch/
enabled=1
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
```
检查当前启用的MySQL仓库
```
shell> yum repolist enabled | grep mysql
```

如果同时启用了多个仓库，安装时会选择最新版本

### 4. 安装MySQL

```
shell> sudo yum install mysql-community-server
```

该命令会安装MySQL服务器 (mysql-community-server) 及其所需的依赖、相关组件，包括mysql-community-client、mysql-community-common、mysql-community-libs等

如果带宽不够，这个步骤时间会比较长，请耐心等待~

### 5. 启动MySQL
* 启动
shell> sudo systemctl start mysqld.service

CentOS 6：

shell> sudo service mysqld start

* 查看状态
shell> sudo systemctl status mysqld.service

CentOS 6：

shell> sudo service mysqld status

* 停止
shell> sudo systemctl stop mysqld.service

CentOS 6：

shell> sudo service mysqld stop

* 重启
shell> sudo systemctl restart mysqld.service

CentOS 6：

shell> sudo service mysqld restart

### 6. 修改密码
初始密码
MySQL第一次启动后会创建超级管理员账号root@localhost，初始密码存储在日志文件中：

shell> sudo grep 'temporary password' /var/log/mysqld.log

修改默认密码
shell> mysql -uroot -p

mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
ERROR 1819 (HY000): Your password does not satisfy the current policy requirements

出现上面的提示是因为密码太简单了，解决方法如下：

使用复杂密码，MySQL默认的密码策略是要包含数字、字母及特殊字符；
如果只是测试用，不想用那么复杂的密码，可以修改默认策略，即validate_password_policy（以及validate_password_length等相关参数），使其支持简单密码的设定，具体方法可以自行百度；
修改配置文件/etc/my.cnf，添加validate_password=OFF，保存并重启MySQL
```
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
Query OK, 0 rows affected (0.00 sec)
```

### 7. 允许root远程访问

```
mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
mysql> FLUSH PRIVILEGES;
```

### 8. 设置编码为utf8

查看编码
```
mysql> SHOW VARIABLES LIKE 'character%';
```

设置编码
编辑/etc/my.cnf，[mysqld]节点增加以下代码：

```
[mysqld]
character_set_server=utf8
init-connect='SET NAMES utf8'
```

### 9. 设置开机启动

shell> systemctl enable mysqld
shell> systemctl daemon-reload