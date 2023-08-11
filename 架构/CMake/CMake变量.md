CMake内置变量

1. **PROJECT_NAME**

   工程名称

2. **VERSION**

   CMake版本

3. **CXX_STANDARD**

   设置C++标准版本

4. **CXX_EXTENSIONS**

   只启用`ISO C++`标准的编译器标志，而不使用特定编译器的扩展

5. **CXX_STANDARD_REQUIRED**

   指定所选标准的版本。如果这个版本不可用，CMake将停止配置并出现错误。当这个属性被设置为`OFF`时，CMake将寻找下一个标准的最新版本，直到一个合适的标志。目前会从`C++20`或`C++17`开始查找, 然后c++14，然后c++11，最后c++98。

