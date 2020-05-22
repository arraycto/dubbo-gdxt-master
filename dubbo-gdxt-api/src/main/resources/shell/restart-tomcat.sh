#!/bin/bash
# 重新启动Tomcat, 置空Tomcat的catalina.out, 删除以*.hprof结尾的文件.
echo "开始重启TcatServer7!~"
# shellcheck disable=SC2046
kill -9 $(ps -ef|grep /usr/local/TcatServer7/bin/|gawk '$0 !~/grep/ {print $2}' |tr -s '\n' ' ')
cat /dev/null > /usr/local/TcatServer7/logs/catalina.out
# shellcheck disable=SC2038
find /usr/local/TcatServer7/bin/ -name '*.hprof' | xargs rm -f
/usr/local/TcatServer7/bin/start.sh
echo "脚本执行完成,TcatServer7正在重启中.."
