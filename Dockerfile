FROM openjdk
WORKDIR /app
COPY /src/main/resources/application-dev.properties /app/application-dev.properties
COPY target/parceiroze-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-Dspring.config.location=/app/application-dev.properties", "-jar", "app.jar"]