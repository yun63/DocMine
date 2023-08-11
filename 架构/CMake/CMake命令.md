# CMake常用命令



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
   set(COMMON_HEADERS 
   		include/detail/class.h
       include/detail/common.h
       include/detail/descr.h
       include/detail/init.h
       include/internals.h
       includedetail/typeid.h)
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

   ```cmake
   ```

10. aux_source_directory

    寻找指定给定目录下的所有源文件并复制给变量

    ```cmake
    aux_source_director (${CMAKE_CURRENT_DIR}/src gemfiled_src)
    ```

    

11. file

    使用正则匹配文件，并将文件路径赋值给第一个参数（为变量）,通常和GLOB搭配

    ```cmake
    file (GLOB gem_src_list ${Root}/*/*.cpp ${Root}/*/*.h ${Root}/*/*.c)
    ```

    

12. include_directories

    将指定目录添加到编译器的头文件搜索路径之下，指定的目录被解释成当前源码路径的相对路径

    ```cmake
    include_directories([AFTER|BEFORE] [SYSTEM] path1 path2...)
    ```

    

13. target_include_directories

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

> > include_directories`影响的是项目级别，而这里的target_include_directories影响的是target级别（而且还可以提供PRIVATE、PUBLIC、INTERFACE关键字），我们应该优先使用