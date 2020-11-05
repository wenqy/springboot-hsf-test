#基础镜像，如果本地仓库没有，会从远程仓库拉取
#FROM openjdk:8-jdk-alpine
FROM dockerhub.wenqy.com/gitlab/gitlabjre:8

#容器中创建目录
#RUN mkdir -p /usr/local/gitlab
#工作目录
WORKDIR /root/

# 下载hsf.sar wget 移到基础镜像
RUN wget http://oss.wenqy.com/aliyun/taobao-hsf.sar-2019-06-stable.jar

#编译后的jar包copy到容器中创建到目录内
COPY target/springboot-hsf-test.jar springboot-hsf-test.jar

# 设定时区
ENV TZ=Asia/Shanghai
RUN set -eux; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
    echo $TZ > /etc/timezone

# hsf 相关环境变量
#ENV jmenv_tbsite_net="-Djmenv.tbsite.net=192.168.1.50"
#ENV jmenv.tbsite.net="192.168.1.50"
#ENV diamond_server_port="-Ddiamond.server.port=8080"
#ENV address_server_port="-Daddress.server.port=8080"
ENV pandora_location="-Dpandora.location=/root/taobao-hsf.sar-2019-06-stable.jar"

# jVN 参数
ENV JAVA_OPTS="\
    -server \
    -Xmx768m \
    -Xms768m \
    -Xmn512m \
    -XX:SurvivorRatio=1 \
    -XX:MetaspaceSize=128m \
    -XX:MaxMetaspaceSize=128m \
    -XX:ParallelGCThreads=4 \
    -XX:+PrintGCDetails \
    -XX:+PrintTenuringDistribution \
    -XX:+PrintGCTimeStamps \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/ \
    -Xloggc:/root/logs/gc.log \
    -XX:+UseGCLogFileRotation \
    -XX:NumberOfGCLogFiles=5 \
    -XX:+DisableExplicitGC \
    -XX:+UseConcMarkSweepGC \
    -XX:+UseParNewGC \
    -XX:+CMSParallelRemarkEnabled \
    -XX:+CMSClassUnloadingEnabled \
    -XX:LargePageSizeInBytes=128M \
    -XX:+UseFastAccessorMethods \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:CMSInitiatingOccupancyFraction=80 \
    -XX:SoftRefLRUPolicyMSPerMB=0 \
    -XX:+PrintClassHistogram \
    -XX:+PrintHeapAtGC \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+PrintFlagsFinal\
    -XX:GCLogFileSize=10M"

#ENV JAVA_TOOL_OPTIONS ${JAVA_TOOL_OPTIONS} ' -Djmenv.tbsite.net='${jmenv.tbsite.net}' -Dpandora.location='${pandora.location}' -Ddiamond.server.port='${diamond.server.port}' -Daddress.server.port='${address.server.port}
### for check the args
#RUN env | grep JAVA_TOOL_OPTIONS

#指定容器启动时要执行的命令
# CMD java -jar springboot-hsf-test.jar
#ENTRYPOINT ["java","-jar","springboot-hsf-test.jar"]
# 传入-Djmenv.tbsite.net属性没有生效
ENTRYPOINT java ${JAVA_OPTS} ${pandora_location} -jar springboot-hsf-test.jar
# 暴露端口
EXPOSE 18081