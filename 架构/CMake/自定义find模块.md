检测外部库：自定义find模块



### 方案

#### 1.1 添加模块到`CMAKE_MODULE_PATH`中，使CMake可以找到模块

```cmake
list(APPEND CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR})
```

#### 1.2 自定义模块，以[FindZeroMQ.cmake](https://github.com/zeromq/azmq/blob/master/config/FindZeroMQ.cmake)为例

#### 1.3 `find_package(ZeroMQ REQUIRED)`

