Libevent支持的事件及属性

Libvent支持的事件是用比特位带标识的，事件包含以下几种：

1. EV_TIMEOUT； 超时
2. EV_READ;  只要网络缓冲区中还有数据，回调函数就会被触发
3. EV_WRITE； 只要塞给网络缓冲区的数据被写完，回调函数就会被触发
4. EV_SIGNAL； POSIX信号量
5. EV_PERSIST; 不指定这个属性回调函数被触发后事件自动删除
6. EV_ET：边缘触发

---

SYN攻击：
  在三次握手过程中，Server发送SYN-ACK之后，收到Client的ACK之前的TCP连接称为半连接（half-open connect），此时Server处于SYN_RCVD状态，当收到ACK后，Server转入ESTABLISHED状态。SYN攻击就是Client在短时间内伪造大量不存在的IP地址，并向Server不断地发送SYN包，Server回复确认包，并等待Client的确认，由于源地址是不存在的，因此，Server需要不断重发直至超时，这些伪造的SYN包将产时间占用未连接队列，导致正常的SYN请求因为队列满而被丢弃，从而引起网络堵塞甚至系统瘫痪。SYN攻击时一种典型的DDOS攻击，检测SYN攻击的方式非常简单，即当Server上有大量半连接状态且源IP地址是随机的，则可以断定遭到SYN攻击了，使用如下命令可以让之现行：
  #netstat -nap | grep SYN_RECV
ddos攻击：
分布式拒绝服务(DDoS:Distributed Denial of Service)攻击指借助于客户/服务器技术，将多个计算机联合起来作为攻击平台，对一个或多个目标发动DDoS攻击，从而成倍地提高拒绝服务攻击的威力。通常，攻击者使用一个偷窃帐号将DDoS主控程序安装在一个计算机上，在一个设定的时间主控程序将与大量代理程序通讯，代理程序已经被安装在网络上的许多计算机上。代理程序收到指令时就发动攻击。利用客户/服务器技术，主控程序能在几秒钟内激活成百上千次代理程序的运行。



多线程还是多进程的争执由来已久，这种争执最常见到在B/S通讯中服务端并发技术的选型上，比如WEB服务器技术中，Apache是采用多进程的（perfork模式，每客户连接对应一个进程，每进程中只存在唯一一个执行线程），Java的Web容器Tomcat、Websphere等都是多线程的（每客户连接对应一个线程，所有线程都在一个进程中）。