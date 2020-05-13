## UDP

#### 1. UDP和TCP之间的差异

> TCP是一个面向连接的数据流协议，TCP在IP报文的基础上，增加了诸如重传、确认、有序传输、拥塞控制等能力，通信的双方在一个确定的上下文中工作；
>
> 而UDP不同，UDP是一个面向无连接的不可靠数据报协议，它并没有类似TCP重传、确认、拥塞控制等能力，可以简单的认为，在IP报文的基础上，UDP增加的能力很有限。

**UDP不保证报文的有序传输，在使用UDP的时候，我们需要做好丢包、重传、报文组装等工作**

使用UDP协议的优势？

* UDP简单，传输效率高，常见的DNS服务就使用UDP协议
* 对时延、丢包等不是特别敏感的场景，UDP也被经常应用，比如多人通信的聊天服务、多人游戏等

#### 2. UDP编程

![UDP](../Assets/UDP.png)

从上图可以看出，UDP编程和TCP编程非常不一样：

服务器创建UDP socket之后，绑定到本地端口，调用recvfrom函数等待客户端的报文发送；

客户端创建socket之后，直接调用sendto函数往目标地址和端口发送UDP报文，然后客户端和服务器端进入互相应答过程。

#### 3. API

```c
#include <sys/socket.h>

ssize_t recvfrom(int sockfd, void *buffer, size_t nbytes, int flags,
                struct sockaddr *from, socklen_t *addrlen);

ssize_t sendto(int sockfd, void *buffer, size_t nbytes, int flags,
              const struct sockaddr *to, socklen_t *addrlen);
```

* recvfrom

  sockfd是本地创建的socket描述符，buffer指向本地的缓冲区，nbytes表示最大可接收数据字节数。

  参数flags暂时总设置为0，之后我们在讨论。

  from和addrlen参数，实际上是返回对端发送方的地址和端口信息，这和TCP非常不一样，TCP是通过accept函数拿到的描述符信息在决定对端的信息。UDP报文每次接收都会获取对端的信息，即UDP报文之间没有上下文。

  recvfrom返回实际接收到的数据字节数。

* sendto

  sockfd是本地创建的socket描述符，buffer指向发送缓冲区，nbytes表示最大发送字节数，flags参数依旧设置为0。

  to和addrlen表示发送的对端地址和端口信息。

  sendto返回世界发送的数据的字节数。

  #### 4. 例子

  ```c
  /*
   * server
   */
  #include <stdio.h>
  #include <stdlib.h>
  #include <unistd.h>
  #include <string.h>
  
  #include <sys/types.h>
  #include <sys/socket.h>
  #include <netinet/in.h>
  #include <arpa/inet.h>
  #include <signal.h>
  
  #define SERV_PORT    12345
  #define MAXLINE      4096
  
  static int count;
  
  
  static void recvfrom_int(int signo) {
      printf("\nreceived %d datagrams\n", count);
      exit(0);
  }
  
  
  int main(int argc, char *argv[])
  {
      int socketfd;
      socketfd = socket(AF_INET, SOCK_DGRAM, 0);
  
      struct sockaddr_in server_addr;
      memset(&server_addr, 0, sizeof(server_addr));
      server_addr.sin_family = AF_INET;
      server_addr.sin_port = htons(SERV_PORT);
      server_addr.sin_addr.s_addr = htons(INADDR_ANY);
  
      if (bind(socketfd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
          perror("bind error");
          exit(EXIT_FAILURE);
      }
  
      socklen_t len;
      char message[MAXLINE];
      count = 0;
  
      signal(SIGINT, recvfrom_int);
  
      struct sockaddr_in client_addr;
      len = sizeof(client_addr);
      for ( ;; ) {
          int n = recvfrom(socketfd, message, MAXLINE, 0, (struct sockaddr *)&client_addr, &len);
          message[n] = 0;
          printf("received %d bytes: %s\n", n, message);
  
          char send_line[MAXLINE];
          sprintf(send_line, "Hi, %s", message);
          sendto(socketfd, send_line, strlen(send_line), 0, (struct sockaddr *)&client_addr, len);
          ++count;
      }
  
      return 0;
  }
  
  
  /*
   * client
   */
  #include <stdio.h>
  #include <stdlib.h>
  #include <unistd.h>
  #include <string.h>
  
  #include <error.h>
  #include <errno.h>
  #include <sys/types.h>
  #include <sys/socket.h>
  #include <netinet/in.h>
  #include <arpa/inet.h>
  #include <signal.h>
  
  #define SERV_PORT    12345
  #define MAXLINE      4096
  
  
  
  int main(int argc, char *argv[])
  {
      if (argc != 2) {
          error(1, 0, "usage: udpclient <IPaddress>");
      }
      int socketfd = socket(AF_INET, SOCK_DGRAM, 0);
  
      struct sockaddr_in server_addr;
      memset(&server_addr, 0, sizeof(server_addr));
      server_addr.sin_family = AF_INET;
      server_addr.sin_port = htons(SERV_PORT);
      inet_pton(AF_INET, argv[1], &server_addr.sin_addr);
  
      socklen_t len = sizeof(server_addr);
  
      struct sockaddr *reply_addr;
      reply_addr = (struct sockaddr *)malloc(len);
      char send_line[MAXLINE], recv_line[MAXLINE + 1];
      int n;
      while (fgets(send_line, MAXLINE, stdin) != NULL) {
          int i = strlen(send_line);
          if (send_line[i - 1] == '\n') {
              send_line[i - 1] = 0;
          }
          printf("now sending %s\n", send_line);
          size_t rt = sendto(socketfd, send_line, strlen(send_line), 0, (struct sockaddr *)&server_addr, len);
          if (rt < 0) {
              error(1, errno, "send failed");
          }
          printf("send bytes: %zu \n", rt);
  
          len = 0;
          n = recvfrom(socketfd, recv_line, MAXLINE, 0, reply_addr, &len);
          if (n < 0) {
              error(1, errno, "recvfrom failed");
          }
          recv_line[n] = '\0';
          fputs(recv_line, stdout);
          fputs("\n", stdout);
      }
  
      return 0;
  }
  
  
  ```

  

#### 5. 总结

* UDP是无连接的数据报协议，和TCP不同，不需要三次握手来建立连接
* UDP通过recvfrom和sendto函数直接接收发数据报报文

