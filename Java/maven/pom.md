### Maven内置属性

```
${basedir}表示项目根目录，即包含pom.xml文件的目录;
${version}表示项目版本;
${project.basedir}同${basedir};
${project.baseUri}表示项目文件地址;
${maven.build.timestamp}表示项目构件开始时间;
${maven.build.timestamp.format}表示属性${maven.build.timestamp}的展示格式,默认值为yyyyMMdd-HHmm,可自定义其格式,其类型可参考java.text.SimpleDateFormat。

用法：
<properties>
　　<maven.build.timestamp.format>yyyy-MM-dd HH:mm:ss</maven.build.timestamp.format>
</properties>
```

### POM属性

```
${project.build.directory}  项目构建输出目录，默认为target/
${project.build.outputDirectory} 项目主代码编译输出目录，默认为target/classes/
${project.build.testOutputDirectory}:项目测试代码编译输出目录，默认为target/testclasses/
${project.build.sourceEncoding}  表示主源码的编码格式;
${project.build.sourceDirectory}  项目的主源码目录，默认为src/main/java/
${project.build.testSourceDirectory} 项目的测试源码目录，默认为/src/test/java/
${project.build.finalName}  表示输出文件名称;
${project.version}  表示项目版本,与${version}相同;
${project.groupId}:项目的groupId
${project.artifactId} 项目的artifactId

用法：
<properties>
　　<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```



### maven-resources-plugin include 通配符

* 全部

  ````java
  
  <resource>
    <directory>src/main/resources</directory>
    <filtering>false</filtering>
    <includes>
      <include>**/*</include>
    </includes>
  </resource>
  ````

* 所有yaml文件，不递归子目录

  ```java
  <resource>
    <directory>src/main/resources</directory>
    <filtering>false</filtering>
    <includes>
      <include>*.yml</include>
    </includes>
  </resource>
  ```

* 所有yaml文件，递归子目录

  ```java
  <resource>
    <directory>src/main/resources</directory>
    <filtering>false</filtering>
    <includes>
      <include>**/*.yml</include>
    </includes>
  </resource>
  ```

  