#!/bin/bash
set encoding=utf-8
CLASSPATH=conf/
for i in lib/*.jar; do
        CLASSPATH=${CLASSPATH}:$i
done
for i in *.jar; do
        CLASSPATH=${CLASSPATH}:$i
done
echo ${CLASSPATH}
java -cp ${CLASSPATH} com.study.discovery.DiscoveryApplication  >> logs/start.out 2>&1 < /dev/null &
echo $! > pid
