FROM openjdk:11
MAINTAINER email@gmail.com
COPY target/demo-0.0.1-SNAPSHOT.jar /opt/brewery/demo.jar
ENTRYPOINT ["java","-jar","/opt/brewery/demo.jar"]