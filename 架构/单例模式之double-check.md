## Singleton在多线程模式下的double-check机制

#### 1. 单线程版本Singleton

```
class Singleton {
public:
    static Singleton *GetInstance();

private:
    Singleton(); // 将构造函数设置为private，确保外部只能通过GetInstance获取对象实例

    static Singleton *instance_;
};


Singleton *Singleton::GetInstance() {
    if (instance_ == NULL) {
        instance_ = new Singleton();
    }
    return instance_;
}

Singleton::Singleton() {
    // initialize
}
```

分析上面的代码，在多线程环境下，Singleton::GetInstance()同时被对多个线程调用，如果第一个线程在if (instance_ == NULL)语句中断挂起，这时刚好其他线程也进入到该代码区域，执行new Singleton(), 对象被new了多次，违背了单例模式的设计初衷。

#### 2. 多线程Singleton

为了保证在多线程模式下，Singleton对象只被创建一次，我们考虑在创建对象时对该代码区域进行互斥锁保护。

```
Singleton *Singleton::GetInstance() {
    lock(mutex);
    if (instance_ == NULL) {
        instance_ = new Singleton();
    }
    unlock(mutex);
    return instance_;
}
```
现在，在多线程模式下，创建单例对象貌似足够安全了，只可能用一个线程创建对象。
然而，引入了一个性能问题：
> 每次调用GetInstance方法都需要执行lock和unlock操作，而事实上，只有第一次调用该方法时才创建对象，之后就直接返回已创建的对象实例即可。

这时我们就应该设计一个Double-Check机制的单例模式

#### 3. 多线程下Double-Check机制的Singleton

[代码](../Sources/Singleton/singleton.h)

```
Singleton::Singleton() {
    if (instance_ == NULL) { // first check
        lock(mutex);
        if (instance_ == NULL) { // second check
            instance_ = new Singleton();
        }
        unlock(mutex);
    }
    return instance_;
}
```
#### 4. 结论：

> 在多线程模式下，使用单例模式最重要的一点就是要保证用Double Check机制保证线程安全

