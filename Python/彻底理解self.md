## 彻底理解类的self关键字

> 问题：为什么类咋定义时需要self，而调用实例的方法时不需要self ？Python为什么不能内部简化从而让我们不去输入self ？

#### 1 self代表类的实例，而非类

```python
class Test(object):
    
    def test(self):
        print(self, self.__class__)
```

self代表的是类Test的实例，而`self.__class__`指向类Test

#### 2 self不必非要写成self

```python
class Test(object):
    
    def test(xxx):
        print(xxx, xxx.__class__)

>>> t = Test()
>>> t.test()
```

如上面代码所示，我们完全可以把self写成xxx，完全没有问题！

只不过强烈建议使用self约定

#### 3 self可以不写吗？

当调用t.test()时，Python解释器内部解释成Test.test(t)，也就是说把self替换成了类的实例。



---

## 总结

* self在定义时需要定义，但是在调用时会自动传入
* self的名字并不是规定死的，但是最好按约定用self
* self总是指调用时的类的实例

