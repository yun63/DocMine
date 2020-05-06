## GDB调试

#### 1. gdb调试命令

1. 列出函数名字

   (gdb) `info functions`

2. 使gdb进入不带调试信息的函数

   (gdb) `set step-mode on`

3. 打印函数堆栈帧信息

   (gdb) `info frame`

4. 设置断点

   * 在地址上设置断点

     （gdb）break *address

   * 在程序入口处设置断点

     （gdb）break main

   * 在文件行号设置断点

     (gdb)  b linum

   * 保存已设置的断点

     在gdb中，可以将设置的断点保存下来

     (gdb) save breakpoints file-name-to-save

     下次调试时，可以设置上次保存的断点

     (gdb) source file-name-to-save

   * 设置临时断点

     临时断点只生效一次

     (gdb) tbreak main

   * **设置条件断点**

     (gdb) break ... if cond

   #### 5. 观察点

   1. 设置观察点

      可以使用`watch`命令设置观察点，当观察的一个变量值发生变化时，程序会停下来。

   2. 设置观察点只针对特定线程生效

   3. 设置读观察点

   4. 设置读写观察点