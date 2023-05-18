FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./*.jar ./app.jar
EXPOSE 8080
CMD java -jar -Xms1024m -Xmx1024m -Duser.timezone=GMT+08 app.jar --spring.profiles.active=