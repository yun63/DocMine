CMake内置变量

1. **PROJECT_NAME**

   工程名称

2. **VERSION**

   CMake版本

3. **CMAKE_CURRENT_SOURCE_DIR**

   当前项目的源代码目录，和顶级CMakeLists.txt同级目录

4. **CMAKE_CURRENT_BINARY_DIR**

   工程编译结果存放的目标目录，可以通过set

5. **CXX_STANDARD**

   设置C++标准版本

6. **CXX_EXTENSIONS**

   只启用`ISO C++`标准的编译器标志，而不使用特定编译器的扩展

7. **CXX_STANDARD_REQUIRED**

   指定所选标准的版本。如果这个版本不可用，CMake将停止配置并出现错误。当这个属性被设置为`OFF`时，CMake将寻找下一个标准的最新版本，直到一个合适的标志。目前会从`C++20`或`C++17`开始查找, 然后c++14，然后c++11，最后c++98。

8. **CMAKE_SYSTEM_NAME**

   CMake为目标操作系统定义了`CMAKE_SYSTEM_NAME`。

   * Linux 
   * Darwin
   * Windows

   > 为了最小化从一个平台转移到另一个平台时的成本，应该避免直接使用Shell命令，还应该避免显式的路径分隔符(Linux和macOS上的前斜杠和Windows上的后斜杠)

9. **CMAKE_BUILD_TYPE**

   * Debug
   * Release
   * RelWithDebInfo
   * MinSizeRel

10. **CMAKE_SIZEOF_VOID_P** 

   定义CPU位数，4->32位，8->64位

11. **CMAKE_CXX_COMPILER_ID**

    C++编译器类型，例如GNU

12. **CMAKE_C_FLAGS**和**CMAKE_CXX_FLAGS **

   C、C++编译器编译选项

11. **CMAKE_INSTALL_PREFIX**

    指定安装目录的根目录，默认是/usr/local

12. **CMAKE_PREFIX_PATH**

   指定要搜索的库文件和头文件的路径

   
