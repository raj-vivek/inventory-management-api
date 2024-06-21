# Stage 1: Build the Spring Project into a JAR file
FROM eclipse-temurin:21 AS builder
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dskiptests

# Stage 2: Run the JAR file from the previous build
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
