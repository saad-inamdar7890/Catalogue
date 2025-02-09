## Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw ./
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

## Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Add environment variables
ENV GCS_BUCKET_NAME=staging.catalogue-447015.appspot.com
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/key.json

# Copy JAR file
COPY --from=build /app/target/catalogue-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]