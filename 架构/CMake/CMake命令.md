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
       
   set(CMAKE_CXX_STANDARD 11)
   set(CMAKE_CXX_STANDARD_REQUIRED ON)
   set(CMAKE_CXX_EXTENSIONS OFF)
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

7. link_directories 

   指定要搜寻的库所在的目录，相当于link工具的 -L参数

   ```cmake
   link_directories(direct1 direct2...)
   ```

8. set_target_properties 给特定目标设置属性

   ```cmake
   set_target_properties(<TARGET>
   	PROPERTIES
   	CXX_STANDARD 11
   	...
   )
   ```

9. target_compile_options 为目标设定编译选项

   ```cmake
   target_compile_options(<TARGET>
   	PRIVATE
   	-O3
   	-g
   )
   ```

10. list 生成源文件列表

   ```cmake
   list(SRCS x.cpp y.cpp)
   ```

11. aux_source_directory

    寻找指定给定目录下的所有源文件并复制给变量

    ```cmake
    aux_source_director (${CMAKE_CURRENT_DIR}/src gemfiled_src)
    ```

    

12. file

    使用正则匹配文件，并将文件路径赋值给第一个参数（为变量）,通常和GLOB搭配

    ```cmake
    file (GLOB gem_src_list ${Root}/*/*.cpp ${Root}/*/*.h ${Root}/*/*.c)
    ```

    

13. include_directories

    将指定目录添加到编译器的头文件搜索路径之下，指定的目录被解释成当前源码路径的相对路径

    ```cmake
    include_directories([AFTER|BEFORE] [SYSTEM] path1 path2...)
    ```

    

14. target_include_directories

    为指定目标添加搜索路径，指定目标是指通过add_executable(), add_library命令生成的，并且**不可以**是alias taget

    ```cmake
    target_include_directories(<TARGET> [SYSTEM] [AFTER|BEFORE]
    	<PRIVATE|INTERFACE|PUBLIC>
    	[path1 path2...]
    )
    ```

    AFTER|BEFORE：添加的路径位于搜索路径的开头或者尾部，默认是AFTER

    * PRIVATE: TARGET对应源文件使用，指定TARGET的属性INCLUDE_DIRECTORIES
    * INTERFACE: TARGET对应的头文件才能使用，指定TARGET的 属性INTERFACE_INCLUDE_DIRECTORIES
    * PUBLIC: TARGET对应的头文件和源文件都能使用，会指定TARGET的属性INCLUDE_DIRECTORIES和INTERFACE_INCLUDE_DIRECTORIES

15. target_sources

    指定目标的源文件列表

    ```cmake
    target_sources(<TARGET> [PRIVATE|INTERFACE|PUBLIC] [sources])
    ```

    

16. target_compile_definitions和add_definitions

    Target_compile_definitions在预处理阶段为特定目标添加定义; add_definitions添加全局定义

    ```cmake
    target_compile_definitions(<TARGET> [PRIVATE|INTERFACE|PUBLIC] [macro])
    
    add_defines(-DGLOBAL_DEFINATION)
    ```

    **注意，如果add_definitions添加的定义是全局可见的，那么，对于使用target_compile_definitions添加的定义将被覆盖**

17. **configure_file**

    由config.h.in生成config.h文件，用于预定义一些宏或者常量

18. **find_file**

    在相应的路径下查找命名文件

    ```cmake
    ```

19. **find_library**

    查找库文件

    ```cmake
    find_library()
    ```

20. **find_package**

    从外部项目查找和加载设置，用于发现和设置包的CMake模块。这些模块包含CMake命令，用于标识系统标准位置中的包。CMake模块文件称为`Find<name>.cmake`，当调用`find_package(<name>)`时，模块中的命令将会运行。

    ```cmake
    find_package(OpenMP REQUIRED) # 查找OpenMp库
    find_package(Boost REQUIRED) # 查找Boost库
    ```

    

21. **find_path**

    查找包含指定文件的目录

22. **find_program**

    找到一个可执行文件

23. **pkg_search_module**

24. **enable_testing**和**add_test**

    enable_testing() 命令开启CMakeLists.txt同级及其子目录下的测试

    add_test()命令用于添加一个测试用例

    ```cmake
    enable_testing()
    add_test(
    	NAME TEST_NAME
    	COMMAND $<TARGET_FILE:executable>
    )
    # $<TARGET_FILE: executable> 是生成器表达式（在构建系统使）
    ```

    

    
