FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install maven -y

WORKDIR /app

COPY  . /app/

RUN mvn clean install -DskipTests &&  mvn clean compile -DskipTests && mvn clean package -DskipTests

EXPOSE 8083


ENTRYPOINT ["java", "-jar", "/app/target/springbootproject1.jar"]







