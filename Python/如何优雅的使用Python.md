#### 1 子类化内置类型很麻烦，应该避免

> 内置类型的方法不会调用子类覆盖的方法

```python
class DoppelDict(dict):
    
    def __setitem__(self, key, value):
        super().__setitem__(key, [value] * 2)

>>> dd = DoppelDict(one=1)
>>> dd
{'one': 1}
>>> dd['two'] = 2
>>> dd
{'one': 1, 'two': [2, 2]}
>>> dd.update(three=3)
>>> dd
{'three': 3, 'one': 1, 'two': [2, 2]}
```

1. DoppelDict.__setitem__ 方法会重复存入的值（只是为了提供易于观察的效果）。它 把职责委托给超类。

2. 继承自 dict 的 __init__ 方法显然忽略了我们覆盖的 __setitem__ 方法：'one' 的 值没有重复
3. [] 运算符会调用我们覆盖的 __setitem__ 方法，按预期那样工作：'two' 对应的是两 个重复的值，即 [2, 2]
4. 继承自 dict 的 update 方法也不使用我们覆盖的 __setitem__ 方法：'three' 的值 没有重复

> 原生类型的这种行为违背了面向对象编程的一个基本原则：始终应该从实例（self）所属的类 开始搜索方法，即使在超类实现的类中调用也是如此

不只实例内部的调用有这个问题（self.get() 不调用 self.__getitem__()），内置类型 的方法调用的其他类的方法，如果被覆盖了，也不会被调用:

```python
class AnswerDict(dict):
    def __getitem__(self, key):
        return 42
    
>>> ad = AnswerDict(a='foo')
>>> ad['a']
42
>>> ad.get(a)
'foo'
>>> d = {}
>>> d.update(ad)
>>> d['a']
'foo'
>>> d
{'a': 'foo'}

```

**结论**

> 直接子类化内置类型（如 dict、list 或 str）容易出错，因为内置类型的方法通 常会忽略用户覆盖的方法。不要子类化内置类型，用户自己定义的类应该继承 collections 模块（http://docs.python.org/3/library/collections.html）中的类，例如 UserDict、UserList 和 UserString，这些类做了特殊设计，因此易于扩展

如果不子类化 dict，而是子类化 collections.UserDict，这样上面的问题就迎刃而解了。

```python
import collections

class DoppelDict2(collections.UserDict):
    def __setitem__(self, key, value):
        super().__setitem__(key, [value] * 2)
        
>>> dd = DoppelDict2(one=1)
>>> dd
{'one': [1, 1]}
>>> dd['two'] = 2
>>> dd
{'two': [2, 2], 'one': [1, 1]}
>>> dd.update(three=3)
>>> dd
{'two': [2, 2], 'three': [3, 3], 'one': [1, 1]}

class AnswerDict2(collections.UserDict):
    def __getitem__(self, key):
        return 42
    
>>> ad = AnswerDict2(a='foo')
>>> ad['a']
42
>>> d = {}
>>> d.update(ad)
>>> d
{'a': 42}
>>> d['a']
42
```

#### 2 多重继承和方法解析顺序（菱形继承问题）

> MRO：方法解析顺序（Method Resolution Order）

多重继承的真实应用：

1. 《设计模式：可复用面向对象软件的基础》一书中的适配器模式用的 就是多重继承
2. 在Python标准库中，最常用的多重继承就是collections.abc包

处理多重继承

1. 把接口继承和实现继承区分开，一定要明确一开始为什么要创建子类
2. 使用抽象基类显示定义接口
3. 通过混入重用代码

> 如果一个类的作用是为多个不相关的子类提供方法实现，从而实现重用，但不体现“是什 么”关系，应该把那个类明确地定义为混入类（mixin class）。从概念上讲，混入不定义新类 型，只是打包方法，便于重用。混入类绝对不能实例化，而且具体类不能只继承混入类

4. 在名称中明确指明混入，强烈建议混入类加Mixin后缀
5. 为用户提供聚合类

> 如果抽象基类或混入的组合对客户端代码非常有用，那就提供一个类，使用易于理解的方式把它们结合起来使用

6. 优先使用对象组合，而不是类继承

