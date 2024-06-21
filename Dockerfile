# Stage 1: Build the Spring Project into a JAR file
FROM eclipse-temurin:21 AS builder
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Environment Variables Setup
ARG MONGODB_DATABASE
ARG MONGODB_USER
ARG MONGODB_PASSWORD
ARG MONGO_CLUSTER
ARG JWT_SIGNING_KEY
ARG CORS_URL

RUN mvn clean package -Dskiptests

# Stage 2: Run the JAR file from the previous build
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

# Environment Variables Setup
ENV MONGODB_DATABASE=${MONGODB_DATABASE}
ENV MONGODB_USER=${MONGODB_USER}
ENV MONGODB_PASSWORD=${MONGODB_PASSWORD}
ENV MONGO_CLUSTER=${MONGO_CLUSTER}
ENV JWT_SIGNING_KEY=${JWT_SIGNING_KEY}
ENV CORS_URL=${CORS_URL}

ENTRYPOINT ["java", "-jar", "app.jar"]
