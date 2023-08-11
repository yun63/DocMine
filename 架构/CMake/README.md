CMake



## 1 设置编译器选项



1. CMake将编译选项视为目标属性。因此，可以根据每个目标设置编译选项，而不需要覆盖CMake默认值
2. 可以使用`-D`CLI标志直接修改`CMAKE_<LANG>_FLAGS_<CONFIG>`变量。这将影响项目中的所有目标，并覆盖或扩展CMake默认值。



### 1.1 编译选项的可见性



1. PRIVATE	

   编译选项会只应用于给定的目标，不会传递给与目标相关的目标

2. INTERFACE

   给定的编译选项将应用于指定目标，并传递给**与目标相关的目标**

3. PUBLIC

   编译选项将应用于指定目标和**使用**它的目标

事例：

```cmake
add_library(geometry
  STATIC
    geometry.hpp
    geometry.cpp
  )
  
target_compile_options(geometry
  PRIVATE
    ${flags}
  )
  
add_executable(main main.cpp)

target_compile_options(main
  PRIVATE
    "-fPIC"
  )

target_link_libraries(main geometry)

```

在上述事例中，及时geometry库被链接到目标main上，目标main也不会继承geometry的编译选项。



### 1.2 跨平台编译选项设定

不同平台的编译器有特性标示，对于特定的编译器选型，其他供应商的编译器不确定是否会理解

可以使用定义编译变量的方法来解决跨平台编译的问题

```cmake
set(COMPILER_FLAGS)
set(COMPILER_FLAGS_DEBUG)
set(COMPILER_FLAGS_RELEASE)

if(CMAKE_CXX_COMPILER_ID MATCHES GNU)
  list(APPEND CXX_FLAGS "-fno-rtti" "-fno-exceptions")
  list(APPEND CXX_FLAGS_DEBUG "-Wsuggest-final-types" "-Wsuggest-final-methods" "-Wsuggest-override")
  list(APPEND CXX_FLAGS_RELEASE "-O3" "-Wno-unused")
endif()
if(CMAKE_CXX_COMPILER_ID MATCHES Clang)
  list(APPEND CXX_FLAGS "-fno-rtti" "-fno-exceptions" "-Qunused-arguments" "-fcolor-diagnostics")
  list(APPEND CXX_FLAGS_DEBUG "-Wdocumentation")
  list(APPEND CXX_FLAGS_RELEASE "-O3" "-Wno-unused")
endif()

target_compile_option(specified-target
  PRIVATE
    ${CXX_FLAGS}
    "$<$<CONFIG:Debug>:${CXX_FLAGS_DEBUG}>"
    "$<$<CONFIG:Release>:${CXX_FLAGS_RELEASE}>"
  )
```

## 2 设定语言标准

#### 2.1 为特定目标设定语言标准

可以通过为目标设置属性的方式来指定语言标准

```cmake
target_set_properties(<TARGET>
	PROPERTIES
	# 设定C++11版本
	CMAKE_CXX_STANDARD 11
	# 指定所选标准的版本。如果这个版本不可用，CMake将停止配置并出现错误。
	# 当这个属性被设置为`OFF`时，CMake将寻找下一个标准的最新版本，直到一个合适的标志。
	CMAKE_CXX_STANDARD_REQUIRED ON
	# 只启用`ISO C++`标准的编译器标志，而不使用特定编译器的扩展
	CMAKE_CXX_EXTANSIONS OFF
	# 编译与位置无关的代码，一般编译动态库时启用
	POSITION_INDEPENDENT_CODE 1
)
```



#### 2.2 设定全局语言标准

如果语言标准是所有目标共享的全局属性，那么可以将`CMAKE_<LANG>_STANDARD`、`CMAKE_<LANG>_EXTENSIONS`和`CMAKE_<LANG>_STANDARD_REQUIRED`变量设置为相应的值。所有目标上的对应属性都将使用这些设置。

如果语言标准是所有目标共享的全局属性，那么可以将`CMAKE_<LANG>_STANDARD`、`CMAKE_<LANG>_EXTENSIONS`和`CMAKE_<LANG>_STANDARD_REQUIRED`变量设置为相应的值。所有目标上的对应属性都将使用这些设置。

## 3 CMake内置变量

###### [CMake变量](CMake/CMake变量.md)

## 4 CMake常用命令

1. project 设置工程名称

   ```cmake
   project(demo LANGUAGES CXX)
   ```

   

2. cmake_minmum_required 设置CMake最小版本

   ```cmake
   cmake_minimum_required(VERSION 3.10)
   ```

   

3. set 设置变量

   ```cmake
   set()
   ```

   

4. add_executable 编译可执行文件

   ```cmake
   add_executable(<TARGET> [sources])
   ```

   

5. add_library 编译库文件

   ```cmake
   add_library(<LIB> 
   	STATIC/SHARED 
   	[sources]
   )
   ```

   

6. target_link_libraries 把指定库连接到目标

   ```cmake
   target_link_libraries(<TARGET> <LIBS>)
   ```

   

7. set_target_properties 给特定目标设置属性

   ```cmake
   set_target_properties(<TARGET>
   	PROPERTIES
   	CXX_STANDARD 11
   	...
   )
   ```

8. target_compile_options 为目标设定编译选项

   ```cmake
   target_compile_options(<TARGET>
   	PRIVATE
   	-O3
   	-g
   )
   ```

9. list 生成源文件列表

10. file

    使用正则匹配文件，并将文件路径赋值给第一个参数（为变量）,通常和GLOB搭配

    ```cmake
    file (GLOB gem_src_list ${Root}/*/*.cpp ${Root}/*/*.h ${Root}/*/*.c)
    ```

    

11. include_directories

    将指定目录添加到编译器的头文件搜索路径之下，指定的目录被解释成当前源码路径的相对路径

    ```cmake
    include_directories([AFTER|BEFORE] [SYSTEM] path1 path2...)
    ```

    

12. target_include_directories

为指定目标添加搜索路径，指定目标是指通过add_executable(), add_library命令生成的，并且**不可以**是alias taget

```cmake
target_include_directories(<TARGET> [SYSTEM] [AFTER|BEFORE]
	<PRIVATE|INTERFACE|PUBLIC>
	[path1 path2...]
)
```

AFTER|BEFORE：添加的路径位于搜索路径的开头或者尾部，默认是AFTER



* PRIVATE: TARGET对应源文件使用，指定TARGET的属性INCLUDE_DIRECTORIES
* INTERFACE: TARGET对应的头文件才能使用，指定TARGETde 属性INTERFACE_INCLUDE_DIRECTORIES
* PUBLIC: TARGET对应的头文件和源文件都能使用，会指定TARGET的属性INCLUDE_DIRECTORIES和INTERFACE_INCLUDE_DIRECTORIES

