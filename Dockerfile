FROM openjdk:17-slim

WORKDIR /app

COPY target/employee-management.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]