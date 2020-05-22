#!/bin/bash
# 重启Java的Jar包
echo "开始重启jar !~"
# shellcheck disable=SC2006
# shellcheck disable=SC2009
pid=`ps -ef |grep search-linux.jar | grep -v grep | awk '{print $2}'`
kill -9 "$pid"
rm -rf /opt/zhangyc/running/search-linux.log
java -jar /opt/zhangyc/running/search-linux.jar >/opt/zhangyc/running/search-linux.log &
echo "jar启动成功 !~"
