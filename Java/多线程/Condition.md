### 使用Condition

> 使用ReentranLock比直接使用synchronized更安全，可以替代synchronized进行线程同步。
>
> 但是，synchronized可以配合wait和notify实现线程在条件不满足时等待，条件满足时唤醒，用ReentranLock怎么实现wait和notify的机制呢？



```java
class TaskQueue {
  private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
```

使用Condition时，引用的condition对象必须从Lock实例的newCondition()返回，这样才能获得一个绑定了Lock实例的condition对象。

Condition提供的await()、signal()、signalAll()原理和synchronized锁队形的wait()、notify()、notifyAll()是一致的，并且行为也是一样的。

1. await()释放当前锁，进入等待状态
2. signal()会唤醒某个等待的线程
3. signalAll()会唤醒所有等待的线程
4. 唤醒线程从await()返回后需要重新获得锁
5. Condition可以替代wait和notify
6. Condition必须从Lock对象获取