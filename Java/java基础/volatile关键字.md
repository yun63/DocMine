### 深入理解Java关键字volatile

#### 1 volatile的用法

`volatile`通常被比喻成`轻量级的synchronized`，和`synchronized`不同，`volatile`修饰的是一个变量，只能用来修饰变量，无法修饰方法及代码块。

#### 2 volatile的原理

为了提高处理器的执行速度，在处理器和内存之间增加了多级缓存来提升，但是由于引入了多级缓存，就存在缓存数据不一致的问题。

对于volatile变量，在进行写操作的时候，JVM会向处理器发送一条lock前缀的指令，将这个缓存中的变量写回到内存中。

但是，就算写回到内存，如果其他处理器缓存的值还是旧的，再执行计算操作就会有问题。所有在多处理器下，为了保证各个处理器的缓存是一致的，就会实现缓存一致性协议。

**缓存一致性协议**： 每个处理器通过嗅探在总线上传播的数据来检测自己缓存的数据是不是过期了，当处理器发现自己缓存行对应的内存地址被修改，就会将当前处理器的缓存行设置成无效状态，当处理器要对这个数据进行操作的时候，就会强制重新从内存里把数据读到处理器缓存。

所以，如果一个变量被volatile所修饰的话，在每次数据变化之后，最新的值都会被强制刷入到主存。而其他处理器的缓存由于遵守了缓存一致性协议，也会把这个变量的值从主存加载到自己的缓存中，这就保证了一个volatile在并发编程中，它的值在多个缓存中是可见的，即保证缓存可见性。

#### 3 volatile与可见性

可见性是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改后的值。

> Java内存模型规定了所有的变量都存储在主内存中，每条线程还有自己的工作内存，线程的工作内存中保存了该线程中是用到的变量的主内存副本拷贝，线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存。不同的线程之间也无法直接访问对方工作内存中的变量，线程间变量的传递均需要自己的工作内存和主存之间进行数据同步进行。所以，就可能出现线程1改了某个变量的值，但是线程2不可见的情况。
>
> 前面的关于`volatile`的原理中介绍过了，Java中的`volatile`关键字提供了一个功能，那就是被其修饰的变量在被修改后可以立即同步到主内存，被其修饰的变量在每次是用之前都从主内存刷新。因此，可以使用`volatile`来保证多线程操作时变量的可见性。