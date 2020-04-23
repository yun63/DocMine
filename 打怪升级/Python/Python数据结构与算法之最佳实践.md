## Python数据结构与算法只最佳实践

#### 1 查找最大或最小的N个元素(Top K)

heapq模块有两个函数: nlargest()和nsmallest()可以完美解决这个问题

```python
>>> import heapq
>>> nums = [1, 8, 2, 23, -4, 7, 18, 23, 42, 37]
>>> heapq.nlargest(3, nums)
[42, 37, 23]
>>> heapq.nsmallest(3, nums)
[-4, 1, 2]

portfolio = [
    {'name': 'IBM', 'shares': 100, 'price': 91.1},
    {'name': 'AAPL', 'shares': 50, 'price': 543.22},
    {'name': 'FB', 'shares': 200, 'price': 21.09},
    {'name': 'HPQ', 'shares': 35, 'price': 31.75},
    {'name': 'YHOO', 'shares': 45, 'price': 16.35},
    {'name': 'ACME', 'shares': 75, 'price': 115.65}
]
cheap = heapq.nsmallest(3, portfolio, key=lambda s: s['price'])
expensive = heapq.nlargest(3, portfolio, key=lambda s: s['price'])

heap = list(nums)
heapq.heapify(head)
```

> 如果在一个集合中查找最小或者最大的N个元素，并且N相对集合总体元素数量比较小的时候，那么heapq.nsmallest(), heapq.nlargest()提供了很好的性能；如果想查唯一最大或最小的元素，max()或min()是更好的选择；如果N的规模和集合规模接近的时候，通常先排序这个结合然后在使用slice，即sorted(items)[:N]或sorted(items)[-N:0]才是更推荐的做法。

#### 2 实现一个优先级队列

可以利用heapq模块实现一个简单的优先级队列

```python
import heapq
class PriorityQueue:
    def __init__(self):
        self._queue = []
        self._index = 0
    
    def push(self, item, priority):
        heapq.heappush(self._queue, (-priority, self._index, item))
        self._index += 1
        
    def pop(self):
        return heapq.heappop(self._queue)[-1]
    
class Item:
    def __init__(self, naem):
        self.name = name
    
    def __repr__(self):
        return 'Item({!r})'.format(self.name)
    
>>> queue = PriorityQueue()
>>> queue.push(Item('foo'), 1)
>>> queue.push(Item('bar'), 5)
>>> queue.push(Item('spam'), 4)
>>> queue.push(Item('grok'), 1)
>>> queue.pop()
Item('bar')
>>> queue.pop()
Item('spam')
```

