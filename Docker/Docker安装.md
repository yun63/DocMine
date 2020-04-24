## CentOS 8.0安装Docker

[参考](https://docs.docker.com/install/linux/docker-ce/centos/)

#### 1 卸载旧版本

```shell
remove docker docker-client \
				  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

#### 2 安装社区版Docker Engine

```
sudo yum install -y yum-utils
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install docker-ce docker-ce-cli containerd.io
```

如果安装报错`Problem: package docker-ce-3:19.03.8-3.el7.x86_64 requires containerd.io >= 1.2.2-3, but none of the providers can be installed`

分析原因：containerd.io >= 1.2.2-3 ，意思就是 containerd.io 的版本必须大于等于 1.2.2-3

#### 3 手动安装 新版本containerd.io

```
wget https://download.docker.com/linux/centos/7/x86_64/edge/Packages/containerd.io-1.2.6-3.3.el7.x86_64.rpm
sudo yum install -y  containerd.io-1.2.6-3.3.el7.x86_64.rpm
sudo yum install docker-ce docker-ce-cli
```

#### 4 启动Docker

```
sudo systemctl start docker
```

