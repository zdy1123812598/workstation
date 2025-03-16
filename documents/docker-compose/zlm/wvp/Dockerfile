FROM openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /app
ADD *.jar /app/wvp.jar
ADD application-dev.yml /app/application-dev.yml
ADD /logs /app/logs
EXPOSE 18088
CMD java -jar -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/heapdump.hprof -Duser.timezone=GMT+08 wvp.jar --spring.profiles.active=dev