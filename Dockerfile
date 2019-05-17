FROM openjdk:8-jdk-alpine
VOLUME /tmp
MAINTAINER Denis Rosa <denis.rosa@couchbase.com>
ARG JAR_FILE=target/kubernetes-starter-kit-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]