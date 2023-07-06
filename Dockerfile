FROM openjdk

WORKDIR /

COPY target/aiko-0.0.1-SNAPSHOT.jar /rest-app.jar

# Expose the default Spring Boot port
EXPOSE 8080

CMD ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-Dspring.devtools.restart.enabled=true", "-jar", "rest-app.jar"]
