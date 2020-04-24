Python常用数据结构

=================

## 1 列表

* list.append

* list.extent(iterable)

* list.insert(i, x)

* list.remove(x)

  > 删除列表中**第1个值**为x的元素，如果没有，则跑出ValueError异常.

* list.pop(i)

  > 删除列表中给定位置的元素并返回它。如果没有给定位置i，l.pop()将删除并返回列表中最后元素。如果是空列表，则抛出IndexError异常。

* list.clear()

* list.index(x[, start[, end]])

* list.count(x)

* list.sort(key=None, reverse=False)

* list.copy()

  > 返回列表的一个浅拷贝

---

## 2 列表作为栈使用（LIFO）



```python
class Stack:
    def __init__(self):
        self.s = []
        
    def push(self, x):
        self.s.append(x)
        
    def pop(self):
        return self.s.pop()
    
    def top(self):
        return self.s[-1]
```



## 3 列表作为队列使用（FIFO)

> 列表可以用作队列，然而作为这个目的相当低效。
>
> 原因：在列表尾部append和pop元素相当快，但是在列表头部insert或者pop却很慢（需要移动其他所有元素）

**collections.deque**被设计用于双端队列。

```python
from collections import deque
q = deque([1, 2, 3])
q.append(4)
q.append(5)
q.popleft() -> 1
q.popleft() -> 2
```



---

## 4 循环技巧



字典: 

```python
for k, v in d.items():
    ...

for k in d.keys():
    ...

for v in d.values():
    ...
    
```

列表：

```python
for i, v in enumerate(lst):
    ...

for v in lst:
    ...
```



