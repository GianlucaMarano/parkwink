FROM openjdk:21-slim
WORKDIR /app
EXPOSE 8080
COPY target/parkwink-0.0.1-SNAPSHOT.jar parkwink.jar
ENTRYPOINT ["java","-jar","parkwink.jar"]