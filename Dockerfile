## Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Set the JAVA_HOME environment variable
#ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk

# Copy the Maven wrapper and make it executable
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code and build the application
COPY src ./src

# Run the build and output logs to a file for debugging
RUN ./mvnw clean package -DskipTests

## Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/catalogue-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]