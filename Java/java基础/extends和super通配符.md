## extends和super通配符

#### 1. extends

使用类似<? extends Number>通配符作为方法的参数时表示

* 方法内部可以调用Number引用的方法，例如 `Number n = obj.getFirst()`
* 方法内部无法调用传入Number引用的方法（null除外），例如：`obj.setFirst(Number n)`
* 总结：使用extends通配符表示参数可以读，但不能写

使用类似<T extends Number>定义泛型时表示

* 泛型类型限定为Number以及Number的子类。

#### 2. super

