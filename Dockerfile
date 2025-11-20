FROM eclipse-temurin:17-jdk
ADD target/demo-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java","-jar","/app.jar"]
EXPOSE 8080
