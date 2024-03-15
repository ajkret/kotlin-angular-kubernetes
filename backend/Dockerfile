# Stage 1: Build the library using Maven
FROM gradle:8.6.0-jdk21 AS app-builder

WORKDIR /app/application
COPY . /app/application
RUN gradle clean build --console=plain
RUN ls -l /app/application/*


FROM openjdk:21-jdk-slim as application

EXPOSE 8080

ENV SERVER_ADDRESS=0.0.0.0
ENV SERVER_PORT=8080
ENV SERVER_SSL_ENABLED=false
ENV STAGE=k8s

COPY --from=app-builder /app/application/build/libs/application.jar application.jar
RUN ls -l
RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["java", "-Xms1024m", "-Xmx2048m", "-Dspring.profiles.active=${STAGE}", "-jar", "/application.jar"]
