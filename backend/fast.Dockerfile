# Stage 1: Build the library using Maven
FROM openjdk:21-jdk-slim

EXPOSE 8080

ENV SERVER_ADDRESS=0.0.0.0
ENV SERVER_PORT=8080
ENV SERVER_SSL_ENABLED=false
ENV STAGE=k8s

COPY build/libs/application.jar application.jar
RUN ls -l
RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-Dspring.profiles.active=${STAGE}", "-jar", "/application.jar"]
