FROM openjdk

WORKDIR /

COPY target/aiko-0.0.1-SNAPSHOT.jar /rest-app.jar

CMD ["java","--add-opens", "java.base/java.time=ALL-UNNAMED", "-jar","rest-app.jar"]