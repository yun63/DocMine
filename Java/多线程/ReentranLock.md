### 使用ReentranLock

#### 与synchronized关键字的区别

```java
// synchronized
public class Counter {
  private int count;
  
  public void add(int n) {
    synchronized (this) {
      count += n;
    }
  }
}

// ReentranLock
public class Counter {
  private int count;
  private ReentrantLock lock;
  public void add(int n) {
    lock.lock();
    try {
      count += n;
    } finally {
      lock.unlock();
    }
  }
}
```

1. Synchronized是Java语言层面提供的语法，不需要考虑异常。ReentranLock必须使用锁，然后在finallly中正确释放锁。

2. ReentranLock是可重入锁，和synchronized一样，一个线程可以多次获取同一个锁

3. ReentranLock可以尝试获取锁，并制定等待时间

   ```java
   if (lock.tryLock(1, TimeUnit.SECONDS)) {
     try {
     	...
   	} finally {
     	lock.unlock();
   	}
   }
   ```

4. ReentranLock可以替代synchronized进行同步，ReentranLock获取锁更安全，使用更灵活。

