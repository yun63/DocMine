## CMake系统变量

1. MAKE_MAJOR_VERSION : major version number for CMake, e.g. the "2" in CMake 2.4.3
2. CMAKE_MINOR_VERSION : minor version number for CMake, e.g. the "4" in CMake 2.4.3
3. CMAKE_VERSION : The version number combined, eg. 2.8.4.20110222-ged5ba for a Nightly build. or 2.8.4 for a Release build.
4. CMAKE_GENERATOR : the generator specified on the commandline.
5. CMAKE_C_COMPILER_ID : one of "Clang", "GNU", "Intel", or "MSVC". This works even if a compiler wrapper like ccache is used.
6. CMAKE_CXX_COMPILER_ID : one of "Clang", "GNU", "Intel", or "MSVC". This works even if a compiler wrapper like ccache is used；
7. cmake_minimum_required：设置所需CMake的最小版本；



#### 编译相关的变量

- CMAKE_CXX_STANDARD：设置C++标准；
- CMAKE_CXX_FLAGS：设置C++编译参数；
- CMAKE_C_FLAGS：设置C编译参数；
- CMAKE_BUILD_TYPE 
- 

```cmake
set (CMAKE_CXX_STANDARD 11)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -w")
set (CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -w")
set (CMAKE_BUILD_TYPE distribution)
set (CMAKE_CXX_FLAGS_DISTRIBUTION "-O3")
set (CMAKE_C_FLAGS_DISTRIBUTION "-O3")
```

