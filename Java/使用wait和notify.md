## wait和notify

`synchronized`解决了多线程竞争的问题，并没有解决多线程协同的问题。

```java
class TaskQueue {
    Queue<String> queue = new LinkedList<>();
    
    public synchronized void addTask(String s) {
        this.queue.add(s);
    }
    
    public synchronized String getTask() {
        while (queue.isEmpty()) {    
        }
        return queue.remove(); 
    }
}
```

上述代码看上去没有问题：getTask先判断队列是否为空，如果为空，就循环等待，直到另一个线程往队列里放入了一个任务，while循环就退出，就可以返回队列的元素了。

但实际上，while循环永远不会退出。因为线程在执行while循环时，已经在getTask入口获取到了this锁，其他线程根本无法调用addTask，因为addTask执行条件也是获取到this锁。

理想效果：

* 线程1可以调用addTask不断往队列中添加任务
* 线程2可以调用getTask从队列中取任务，如果队列为空，则getTask应该等待队列至少有一个任务时再返回

因此，多线程协调运行的原则就是：

当条件不满足时，线程进入等待状态；当条件满足时，线程被唤醒，继续执行任务。

```java
public synchronized String getTask() {
    while (queue.isEmpty()) {
        this.wait(); // 释放this锁，让addTask有机会向队列添加任务
        // 重新获取this锁
    }
    return queue.remove();
}
```

当一个线程执行到`getTask()`方法内部的`while`循环时，它必定已经获取到了`this`锁，此时，线程执行`while`条件判断，如果条件成立（队列为空），线程将执行`this.wait()`，进入等待状态。

这里的关键是：`wait()`方法必须在当前获取的锁对象上调用，这里获取的是`this`锁，因此调用`this.wait()`。

调用`wait()`方法后，线程进入等待状态，`wait()`方法不会返回，直到将来某个时刻，线程从等待状态被其他线程唤醒后，`wait()`方法才会返回，然后，继续执行下一条语句。

**wait执行机制**

* wait不是一个普通的Java方法，而是定义再Objec类的一个native方法（由JVM的C代码实现）
* 必须在synchronized块中才能调用wait方法，因为wait方法调用时会释放线程获得的锁，wait方法返回后，线程会再次试图获得锁。
* 因此，只能在锁对象上调用wait方法

```java
public synchronized void addTask(String s) {
    this.queue.add(s);
    this.notify(); // 唤醒在this锁等待的对象
}
```

