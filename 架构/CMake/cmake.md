CMake里的变量

### 1 最简单的CMake配置

```cmake
# 定义cmake最小要求版本
cmake_minimum_required (VERSION 2.6)
# 定义project名称
project (Tutorial)
# 指定可执行文件
add_executable(Tutorial tutorial.cxx)
```

