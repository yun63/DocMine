Libevent支持的事件及属性

Libvent支持的事件是用比特位带标识的，事件包含以下几种：

1. EV_TIMEOUT； 超时
2. EV_READ;  只要网络缓冲区中还有数据，回调函数就会被触发
3. EV_WRITE； 只要塞给网络缓冲区的数据被写完，回调函数就会被触发
4. EV_SIGNAL； POSIX信号量
5. EV_PERSIST; 不指定这个属性回调函数被触发后事件自动删除
6. EV_ET：边缘触发

---