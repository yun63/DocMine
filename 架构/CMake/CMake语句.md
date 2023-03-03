CMake使用

#### CMake语句

CMake语句有3类用法：

1. 设置变量

   * set

     ```cm
     set(COMMON_HEADERS 
     		include/detail/class.h
         include/detail/common.h
         include/detail/descr.h
         include/detail/init.h
         include/internals.h
         includedetail/typeid.h)
     ```

   * file 

     使用正则匹配文件，并将文件路径赋值给第一个参数（为变量）,通常和GLOB搭配

     ```cmake
     file (GLOB gem_src_list ${Root}/*/*.cpp ${Root}/*/*.h ${Root}/*/*.c)
     ```

   * list

     针对list进行各种操作，如增删改查

     ```cmake
     list (REMOVE_ITEM ...)
     list (APPEND ...)
     ```

     

   * find_library

     寻找一个库，将找到的库的绝对路径赋值给变量

     ```cmake
     find_library (LIBGEMFIELD_PATH libgemfield.so PATHS ${CUDA_TOOLKIT_ROOT_DIR}/lib64/)
     ```

   * aux_source_directory

     寻找指定给定目录下的所有源文件并复制给变量

     ```cmake
     aux_source_director (${Root}/src gemfiled_src)
     ```

2. 设置target，也就是构建的目标是什么

   * add_executable

     ```cmake
     add_executable (server server.cpp)
     ```

   * add_library

     ```cmake
     add_library(<name> [STATIC | SHARED | MODULE]
                 [EXCLUDE_FROM_ALL]
                 source1 [source2 ...])
     
     #举例
     add_library(gemfield_static STATIC ${gemfield_src_list})
     ```

     

3. 设置target属性，也就是定义如何生成target（比如源文件的路径、编译选项、要链接什么库...）

   * add_definitions

   * target_link_libraries

     ```cmake
     target_link_libraries (
             server
             shared_static
             json_static
             mpeg_static
             ${LINK_LIB_LIST})
     ```

   * target_include_directories

     编译target时，指定其需要include的目录。target必须提前使用add_executable()或者add_library()定义。

     `include_directories`影响的是项目级别，而这里的target_include_directories影响的是target级别（而且还可以提供PRIVATE、PUBLIC、INTERFACE关键字），我们应该优先使用target_include_directories。

     ```cmake
     target_include_directories (server
     		PUBLIC
     		${CMAKE_SOURCE_DIR}/gemfield/include)
     ```

   * include_directories

     将目录中加入搜索头文件时的路径，不支持通配符

     ```cmake
     include_directories (${CMAKE_SOURCE_DIR}/gemfield/include)
     ```

   * link_directories 

     指定要搜寻的库所在的目录，相当于link工具的 -L参数

     ```cmake
     link_directories (directory1 directory2 ...)
     ```

     这个命令很少使用，最好使用`find_library()`代替。
