add_executable命令

### 格式及说明

1. 普通可执行目标文件


   ```cm
   add_executable (<name> [WIN32] [MACOSX_BUNDLE]
   		[EXECUDE_FROM_ALL]
   		[source1] [source2 ...])
   ```

   通过指定的源文件列表构建出可执行目标文件。

   

   * name: 可执行目标文件的名字，全局唯一。
   * WIN32: 用于windows系统下创建一个以WinMain为入口的可执行目标文件。
   * MAXOSX_BUNDLE：用于mac系统或者iOS系统下创建一个GUI可执行应用程序。
   * EXECUDE_FROM_ALL：用于指定可执行目标是否会被构建，当该选项使用的时候，可执行目标不会被构建。
   * [source1] [source2 ...]：够哦建可执行目标文件所需要的源文件。也可以同步`target_sources()`继续为可执行目标文件添加源文件（需要在调用`target_sources()`之前，可执行目标文件已经通过`add_executable`或者`add_library`定义过了）。

   ```cmake
   # CMakeLists.txt
   cmake_minimum_required (VERSION 3.10.2)
   # 设置可执行目标文件的输出目录
   SET(CMAKE_RUNTIME_OUTPUT_DIRECTORY output)
   
   include_directories (sub)
   add_subdirectory (sub)
   
   add_executable (runtest main.cpp)
   target_sources (runtest test.cpp)
   ```

   test.h

   ``` c++
   // test.h
   #include <string>
   void test(std::string str);
   
   // test.cpp
   #include "test.h"
   #include <iostream>
   void test(std::string str) {
     std::cout << "In test: " << str << std::endl;
   }
   
   // main.cpp
   #include "test.h"
   int main() {
     test("Hello, world!");
     return 0;
   }
   ```

   执行cmake . && make后，在output目录生成可执行目标文件runtest

2. 导入可执行目标文件

3. 别名可执行文件

```cmak
1. add_executable (<name> [WIN32] [MACOSX_BUNDLE]
      [EXCLUDE_FROM_ALL]
      [source1] [source2 ...])
2. add_executable (<name> IMPORTED [GLOBAL])
3. add_executable (<name> ALIAS <target>)

使用指定的源文件来生成目标可执行文件。这里的目标可执行文件分为三类：普通可执行目标文件、导入可执行目标文件、别名可执行目标文件。分别对应上面的三种命令格式。
```

