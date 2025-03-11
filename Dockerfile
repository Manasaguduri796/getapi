FROM openjdk:21-jdk-slim

WORKDIR /app


RUN mvn clean package

COPY target/springbootproject1-0.0.1-SNAPSHOT.jar /app/springbootproject1.jar

EXPOSE 8083


ENTRYPOINT ["java", "-jar", "/app/springbootproject1.jar"]

FROM maven:3.8.6-open-jdk




