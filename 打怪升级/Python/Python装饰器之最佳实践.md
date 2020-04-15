## 装饰器最佳实践

#### 1 用类来实现装饰器

绝大多数装饰器都是基于函数和闭包实现的，但这并不是实现装饰器的唯一方式。

Python对某个对象能否通过装饰器来实现(@decorator)只有一个要求：对象可调用（callable）。这样，用类来实现装饰器就变得简单了，只要使类可调用，那就可以用类来实现装饰器了，显然，只要自定义类的`__call__`魔法方法即可。

例子：基于类实现一个延迟功能的装饰器@delay

```python
import time
import functools

class DelayFunc(object):
    def __init__(self, duration, func):
        self.duration = duration
        self.func = func
    
    def __call__(self, *args, **kwargs):
        time.sleep(self.duration)
        return self.func(*args, **kwargs)
    
    def nodelay(self, *args, **kwargs):
        return self.func(*args, **kwargs)
    

def delay(duration):
    # 此处为了避免定义额外函数，直接使用 functools.partial 帮助构造
    # DelayFunc 实例
    return functools.partial(DelayFun, duration)


# 如何使用？
@delay(duration=2)
def foo(a, b):
    print(a, b)
    
foo(1, 2)  # 延迟2秒执行
foo.nodelay(1, 2)  # 立即执行

```

