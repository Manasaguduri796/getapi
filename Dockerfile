FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/springbootproject1-0.0.1-SNAPSHOT.jar /app/springbootproject1.jar

EXPOSE 8083


ENTRYPOINT ["java", "-jar", "/app/springbootproject1.jar"]


