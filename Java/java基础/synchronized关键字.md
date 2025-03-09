### synchronized关键字

> Java为了解决并发编程中存在的原子性、可见性和有序性问题，提供了一系列和并发处理相关的关键字，比如synchronized、volatile、final、currency包等



#### synchronized的用法

1. 同步方法
2. 同步代码块

#### synchronized的实现原理

结论：

1. 对于同步方法，JVM采用ACC_SYNCHRONIZED标记符来实现同步
2. 对于同步代码块，JVM采用monitorenter、monitorexit两个指令来实现同步

> 方法级的同步是隐式的。同步方法的常量池中会有一个`ACC_SYNCHRONIZED`标志。当某个线程要访问某个方法的时候，会检查是否有`ACC_SYNCHRONIZED`，如果有设置，则需要先获得监视器锁，然后开始执行方法，方法执行之后再释放监视器锁。这时如果其他线程来请求执行方法，会因为无法获得监视器锁而被阻断住。值得注意的是，如果在方法执行过程中，发生了异常，并且方法内部并没有处理该异常，那么在异常被抛到方法外面之前监视器锁会被自动释放。
>
> 同步代码块使用`monitorenter`和`monitorexit`两个指令实现。可以把执行`monitorenter`指令理解为加锁，执行`monitorexit`理解为释放锁。 每个对象维护着一个记录着被锁次数的计数器。未被锁定的对象的该计数器为0，当一个线程获得锁（执行`monitorenter`）后，该计数器自增变为 1 ，当同一个线程再次获得该对象的锁的时候，计数器再次自增。当同一个线程释放锁（执行`monitorexit`指令）的时候，计数器再自减。当计数器为0的时候。锁将被释放，其他线程便可以获得锁。

无论是`ACC_SYNCHRONIZED`还是`monitorenter`、`monitorexit`都是基于monitor实现的，monitor是基于c++实现的。