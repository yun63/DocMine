### Docker操作指南 

​		——原文出自[语雀]((https://www.yuque.com/docs/share/51dc3ac7-b841-4a29-bca1-13227d258073))

> 在 Linux 上，提供额外软件抽象层，以及操作系统虚拟化自动管理机制。利用了内核资源分离机制（cgroups、namespace）创建独立容器（container）。

[Docker Document](https://docs.docker.com/)

[Docker Command Line](https://docs.docker.com/engine/reference/commandline/docker/)

---

#### 1 镜像

* 从Docker Hub中搜索镜像

```
$ docker search [OPTIONS] TERM
```

* 列出所有安装的镜像

```
$ docker images [OPTIONS] [REPOSITORY[:TAG]]
```

* 管理命令

```
$ docker image history
$ docker image pull, docker pull
$ docker image rm, docker rmi
```

#### 2 容器

1. 创建容器

```shell
$ docker create [OPTIONS] IMAGE [COMMAND] [ARG...]
```

> 如需连接终端，那么使用 ti 参数。
>
> 设置 name，hostname 便于标识。
>
> 如使用 GDB 等软件，建议开启 privileged 设置

- * -**t**: 分配 TTY。
- - -**i**: 保持 stdin 打开。
- - --**name**: 容器名称。
  - --**hostname**, -**h**: 主机名。
  - --**workdir**, -**w**: 工作目录。
  - --**rm**: 退出时自动删除容器。
  - --**env**, -**e**: 环境变量。
  - --**memory**, -**m**: 内存限制。
- - --**publish**, -**p**: 向宿主发布容器端口。 `-p host_port:container_port`
  - --**volume**, -**v**: 绑定共享目录（卷）。 `-v host_path:container_path`，`-v volume:contailer_path`
  - --**read-only**: 容器 root 文件系统只读，只能读附加卷。
- - --**restart**: 重启策略。(默认 no。可选 on-failure, always, unless-stopped)
  - --**ulimit**: 设置 ulimit 参数。
  - --**privileged**: 赋予真 root 权限。

直接使用 `docker run` 命令完成 create + start 操作。

比如，临时创建一个容器运行某个命令，附加 rm 参数在退出后删除。

```
$ docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

2. 容器列表

```
$ docker ps [OPTIONS]
```

- --**all**, -**a**: 所有容器。（默认仅输出正在运行的容器）

3. 删除容器

$ docker r [OPTIONS] CONTAINER [CONTAINER...]

* --**force**, -**f**: 强制删除正在运行的容器

4. 容器重命名

$ docker rename CONTAINER NEW_NAME

#### 3 运行

启动容器，可直接打开终端连接。

- -**a**: 连接（attach）
- -**i**: 交互（stdin）

连接到正在运行的容器。

```
$ docker attach [OPTIONS] CONTAINER
```

> 连接后，使用 `CTRL+P, CTRL+Q` 退出，但不停止容器。
>
> 参数 **no-stdin** 适合观看演示的只读方式（输入不影响主端），`CTRL+C` 退出。

在已运行的容器内执行命令。

```
$ docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
```

- --**tty**, -**t**: 启动终端，与 -**i** 参数一起使用。
- --**interactive**, -**i**: 交互模式。

- --**workdir**, -**w**: 工作目录。
- --**detach**, -**d**: 后台运行。
- --**privileged**: 特权模式。

#### 4 停止

暂停容器

```
$ docker pause CONTAINER [CONTAINER...]
$ docker unpause CONTAINER [CONTAINER...]
```

停止容器

```
$ docker stop [OPTIONS] CONTAINER [CONTAINER...]
$ docker kill [OPTIONS] CONTAINER [CONTAINER...]
```

> stop 可使用 --time, -t 参数指定等待秒数（默认 10）。
>
> kill 可使用 --singal, -s 指定信号（默认 KILL）。

重启容器

```
$ docker restart [OPTIONS] CONTAINER [CONTAINER...]
```

- --**time**, -**t**: 等待时间（默认 10 秒）。

#### 5 状态

输出容器记录

```
$ docker logs [OPTIONS] CONTAINER
```

- --**fllow**, -**f**: 跟随输出。
- --**tail**: 最后 n 行（默认 all）。

显示容器状态

```
$ docker stats [OPTIONS] [CONTAINER...]
```

执行 top 命令，查看进程状态。

```
$ docker top CONTAINER [ps OPTIONS]
```

#### 6 卷

创建卷

```
$ docker volume create [OPTIONS] [VOLUME]
```

- --**name**: 名称。

卷管理

```
$ docker volume ls [OPTIONS]
$ docker volume rm [OPTIONS] VOLUME [VOLUME...]
```

#### 7 文件

在宿主和容器间拷贝文件

```
$ docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH
$ docker cp [OPTIONS] SRC_PATH CONTAINER:DEST_PATH
```

