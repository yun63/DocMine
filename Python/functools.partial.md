## 彻底理解functools.partial

#### 1 functools.partial

> 和装饰器一样，偏函数可以扩展函数的功能，但又不完全等价于装饰器。

> functools.partial(func, *args, **keywords)
> Return a new partial object which when called will behave like func called with the positional arguments args and keyword arguments keywords. If more arguments are supplied to the call, they are appended to args. If additional keyword arguments are supplied, they extend and override keywords. Roughly equivalent to:
>
> 简单翻译: 它返回一个偏函数对象，这个对象和 func 一样，可以被调用，同时在调用的时候可以指定位置参数 (*args) 和 关键字参数(**kwargs)。如果有更多的位置参数提供调用，它们会被附加到 args 中。如果有额外的关键字参数提供，它们将会扩展并覆盖原有的关键字参数。它的实现大致等同于如下代码:
>
> ```python
> def partial(func, *args, **kwargs):
>        def newfunc(*fargs, **fkwargs):
>            newkws = kwargs.copy()
>            newkws.update(fkwargs)
>            return func(*args, *fargs, **newkws)
>        newfunc.func = func
>        newfunc.args = args
>        newfunc.kwargs = kwargs
>        return newfunc
> ```
>
> 