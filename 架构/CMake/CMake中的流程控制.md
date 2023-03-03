### CMake中的流程控制

####  if else

```cmake
#if
if(逻辑表达式)
...
endif()

#if else
if(逻辑表达式)
...
else()
...
endif()

#if elseif
if(逻辑表达式)
...
elseif(逻辑表达式)
...
endif()
```

#### for循环

```cmake
foreach(变量 变量的列表)
...
endforeach()
```

