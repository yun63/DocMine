# SimpleChannelInboundHandler VS ChannelInboundAdapter



通常，Netty来发送和接收数据都会继承SimpleChannelInboundHandler和**ChannelInboundHandlerAdapter**这两个抽象类,

客户端继承SimpleChannelInboundHandler，服务端继承ChannelInboundAdapter，这是为什么呢？



最主要的区别就是SimpleChannelInboundHandler在接收到数据后会自动release掉数据占用的Bytebuffer资源(自动调用Bytebuffer.release())。而为何服务器端不能用呢，因为我们想让服务器把客户端请求的数据发送回去，而服务器端有可能在channelRead**方法**返回前还没有写完数据，因此不能让它自动release。



## 1 SimpleChannelInboundHandler



## 2 ChannelInboundAdapter









