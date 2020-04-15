## 元类基础（metaclass）

——元类是创建类的类。

### 1 元类是用于构建类的类

默认情况下，Python 中的 类是 type 类的实例。也就是说，type 是大多数内置的类和用户定义的类的元类

1. object和type

> object 类和 type 类之间的关系很独特：object 是 type 的实例，而 type 是 object 的子类。这种关系很“神奇”，无法使用 Python 代码表述，因为定义其中一个之前 另一个必须存在。type 是自身的实例这一点也很神奇

2. Metaclass

> **所有类都是type的实例，但是元类还是type的子类，因此可以作为创建类的工厂**

> 具体来说，元类可以通过实现`__init__`方法定制实例，元类的`__init__`方法可以做到类装饰器能做的任何事情，但是作用更大。
>
> ```python
> class MetaClass(type):
> 	def __init__(cls, name, bases, dic):
> ```

> **编写元类时，通常会把第一个参数改成 cls，以清晰地表明要构建的实例是类**

> **如果想进一步定制类，可以在元类中实现 __new__ 方法。不过，通常情况下实 现 __init__ 方法就够了。**

> **Python 2.7 使用的是 __metaclass__ 类属性，类的声明体不支持 metaclass= 关键字参数**

3. 类作为对象