FROM gradle:8.2.1-jdk17

COPY . /

WORKDIR /

RUN gradle build 

# Expose the default Spring Boot port
EXPOSE 8080

CMD ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-Dspring.devtools.restart.enabled=true", "-jar", "build/libs/aiko-0.0.1-SNAPSHOT.jar"]
