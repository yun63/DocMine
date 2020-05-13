## TCP三次握手：使用socket建立连接

#### 1. 服务器

1. 创建socket

   ```c
   int socket(int domain, int type, int protocol);
   ```

   domain：地址族

   * AF_INET
   * AF_INET6
   * PF_INET
   * PF_NET6
   * PF_LOCAL

   type：

   * SOCK_STREAM 字节流 -> TCP
   * SOCK_DGRAM 数据报 -> UDP
   * SOCK_RAW 原始socket

   protocol：

   原本用来指定通信协议，基本废弃，因为由domain和type已经指定完成，目前protocol参数指定为0即可。

2. 绑定

   ```c
   int bind(int fd, sockaddr *addr, socklen_t len);
   ```

    bind第二个参数是通用地址结构sockaddr，需要注意的是：虽然这里接收的是通用地址结构，实际上传入的参数可能是IPv4、IPv6或者本地socket地址结构。bind函数会根据len参数来判断`sockaddr *addr`该怎么解析，参数len表示的就是传入的地址长度，是一个可变长的值。

   其实可以把bind函数理解成这样：

   ```c
   int bind(int fd, void *addr, socklen_t len);
   ```

   不过BSD设计socket的时候C语言还没有void *类型，为了解决这个问题，BSD的设计者创造性地设计了通用地址结构sockaddr作为支持bind和accept等函数的参数。

   对于使用者来说，每次需要将IPv4、IPv6或者本地socket地址结构转化为通用地址结构

   ```c
   struct sockaddr_in addr;
   bind(sock, (struct sockaddr *)&addr, sizeof(addr));
   ```

   对于实现者来说，可以根据该地址结构的前两个字节（sin_family、sin6_family）来判断地址族。为了处理长度可变的结构，需要读取函数的第三个参数len，这样就可以对地址进行解析了。

   针对IP和端口的处理：

   > 我们可以把地址设置成本机的IP地址，这相当于告诉内核，仅仅对目标IP是本机IP的IP包进行处理。
   >
   > 但这样写的程序在部署时时一个问题：我们编写应用程序时并不清楚自己的应用程序将会被部署到哪些环境。这个时候，可以利用**通配地址**的能力帮助我们解决这个问题。
   >
   > 通配地址相当于告诉内核：只要目标地址是本机的都可以。例如，一台机器由两块网卡，IP地址分别是202.61.22.55和192.168.1.11，那么想这两个IP请求的数据包都会被我们编写的应用程序处理。

   对于IPv4的地址，使用INADDR_ANY来完成通配地址的设置；

   对于IPv6的地址，使用IN6ADDR_ANY来完成通配地址的设置。

   ```c
   struct sockaddr_in addr;
   addr.sin_addr.s_addr = htonl(INADDR_ANY); /* IPv4通配地址 */
   ```

   如果把端口设置为0，就相当于把端口的选择权交给内核来处理，内核会根据一定的算法选择一个空闲的端口，完成socket的绑定，服务器端不常用，服务器端口是熟知的。

   我们来初始化一个IPv4的socket例子：

   ```c
   #include <stdio.h>
   #include <stdlib.h>
   #include <sys/socket.h>
   #include <netinet/in.h>
   
   int make_socket(uint16_t port) {
       int sock;
       struct sockaddr_in addr;
   
       sock = socket(PF_INET, SOCK_STREAM, 0);
       if (sock < 0) {
           perror("socket error");
           exit(EXIT_FAILURE);
       }
   
       addr.sin_family = AF_INET;
       addr.sin_port = htons(port);
       addr.sin_addr.s_addr = htonl(INADDR_ANY);
   
       if (bind(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
           perror("bind error");
           exit(EXIT_FAILURE);
       }
       return sock;
   }
   
   ```

3. listen

   ```c
   int listen(int sockfd, int backlog);
   ```

   初始化socket，可以认为是一个“主动”的socket，其目的是之后主动发起请求（connect）。

   通过listen函数可以将原来主动socket转换为被动socket，告诉内核：我这个socket是用来等待客户端请求的。当然内核会为此做好接收客户端请求的一切准备，比如完成连接队列。

   backlog：未完成连接队列的大小，这个参数决定了可以接收的并发数目。这个参数越大，并发数据理论上也会越大，但是参数过大会占用过多的内核资源，一些系统，比如linux是不允许对这个参数新型改变。

4. accept

   ```c
   int accept(int listensockfd, struct sockaddr *cliaddr, socklen_t *addrlen);
   ```

   accept函数的第一个参数是socket，可以叫它listen socket。

   函数的返回值有两部分，一部分是：

   struct sockaddr *cliaddr是通过指针方式获取的客户端的地址

   socklen_t *addrlen 告诉我们地址的大小，可以理解成当我们拿起电话机时，看到了来电显示，知道了对方的电话号码；

   另一部分时函数的返回值，这个返回值是一个全新的socket，代表了与客户端的连接。

   > 这里一定要注意有两个socket，第一个是监听socket，它是作为参数存在的；
   >
   > 第二个是返回已连接的socket，代表与客户端的连接。


#### 2. 客户端

客户端建立连接的第一步也是创建socket，然后通过调用connect函数向服务器发起建立连接请求。

1. connect

   ```c
   int connect(int sockfd, const struct sockaddr *serveraddr, socklen_t addrlen);
   ```

   connect函数的第一个参数sockfd是客户端创建的socket，第二个、第三个参数serveraddr和addrlen分别代表指向socket地址结构的指针和该结构的大小。serveraddr必须含有服务器的IP地址和端口号。

   客户端在调用connect函数之前不必非得调用bind函数，因为内核会去顶源IP地址，并按照一定的算法选择一个临时端口作为源端口。

   如果是TCP，那么调用connect函数将激发TCP的3次握手过程，而且仅在连接建立成功或者出错才返回。其中出错返回可能有以下3种情况：

   * 三次握手无法建立，客户端发出的SYN包没有任何响应，于是返回TIMEOUT错误。这总情况比较常见的原因是对应的服务端IP写错。

   * 客户端收到了RST复位应答，这时候客户端回立即返回`ONNECTION REFUSED`错误。这种情况比较常见于客户端发送连接请求的端口写错，因为RST是TCP在发生错误时发送的一种TCP分节。

     产生RST的三个条件：

     * 目的地为某端口的SYN到达，然而该端口没有正在监听的服务器
     * TCP想取消一个已有连接
     * TCP接收到一个根本不存在的连接上的分节

   * 客户发出的 SYN 包在网络上引起了"destination unreachable"，即目的不可达的错误。这种情况比较常见的原因是客户端和服务器端路由不通。

   ![三次握手](../Assets/三次握手.png)

#### 3. 重头戏：TCP三次握手解读

> 我们先看一下最初的过程，服务器端通过 socket，bind 和 listen 完成了被动套接字的准备工作，被动的意思就是等着别人来连接，然后调用 accept，就会阻塞在这里，等待客户端的连接来临；客户端通过调用 socket 和 connect 函数之后，也会阻塞。接下来的事情是由操作系统内核完成的，更具体一点的说，是操作系统内核网络协议栈在工作。

三次握手具体过程：

1. 客户端的协议栈向服务器端发送SYN包，并告诉服务器端当前发送序列号是j，客户端进入SYNC_SENT状态
2. 服务器端的协议栈收到这个包后，和客户端进行ACK确认，应答的值为j+1，表示对SYN包j的确认，同时服务器也发送一个SYN包，告诉客户端当前我的发送序列号是k，服务器端进入SYNC_RCVD状态
3. 客户端协议栈收到ACK之后，使得应用程序从connect调用返回，表示客户端到服务器端的**单向**连接建立成功，客户端的状态变为ESTABLISHED客户端协议栈也会对服务器端的SYN包进行确认，确认数据为K+1
4. 确认包到达服务器端后，服务器端协议栈使得accept阻塞调用返回，这个时候服务器端到客户端的单向连接也建立成功，服务器端也进入ESTABLISHED状态。至此，三次握手完成，客户端到服务器端的双向连接建立成功。

形象一点的比喻是这样的，有 A 和 B 想进行通话：

- A 先对 B 说：“喂，你在么？我在的，我的口令是 j。”
- B 收到之后大声回答：“我收到你的口令 j 并准备好了，你准备好了吗？我的口令是 k。”
- A 收到之后也大声回答：“我收到你的口令 k 并准备好了，我们开始吧。”



---



#### 4. 总结

- 服务器端通过创建 socket，bind，listen 完成初始化，通过 accept 完成连接的建立。
- 客户端通过场景 socket，connect 发起连接建立请求。
- 三次握手保证客户端和服务器建立双向连接