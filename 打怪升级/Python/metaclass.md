## 元类基础（metaclass）

——元类是创建类的类。

### 1 元类是用于构建类的类

默认情况下，Python 中的 类是 type 类的实例。也就是说，type 是大多数内置的类和用户定义的类的元类

1. object和type

> object 类和 type 类之间的关系很独特：object 是 type 的实例，而 type 是 object 的子类。这种关系很“神奇”，无法使用 Python 代码表述，因为定义其中一个之前 另一个必须存在。type 是自身的实例这一点也很神奇

