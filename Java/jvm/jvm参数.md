JVM参数

==========

```shell
# -javaagent:/path_to/jmx_prometheus_javaagent-0.12.0.jar=23001:/home/sh/sh_server/jmx/jmx_exporter.yml
-Xbootclasspath/a:/usr/local/jdk1.8.0_281/lib/tools.jar
-XX:+HeapDumpOnOutOfMemoryError
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=256m
-Xms11g
-Xmx11g
-XX:SurvivorRatio=8
-Xss1M
-XX:MaxTenuringThreshold=10
-XX:G1HeapRegionSize=4M
-Xmn3g
```



filebeat-7.9.2-linux-x86_64
logstash

```shell
nohup /home/data/software/filebeat-7.9.2-linux-x86_64/filebeat -e -c /home/data/software/filebeat-7.9.2-linux-x86_64/filebeat.yml > filebeat.log &
```

elasticsearch
Kibana
