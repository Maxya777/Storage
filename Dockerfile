FROM openjdk:17-oracle
EXPOSE 9090
ADD target/Storage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]