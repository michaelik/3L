# Use the official Gradle image with JDK 17
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . .

# Build the application
RUN gradle clean build -x test

# Use a lightweight JDK runtime for the final image
FROM eclipse-temurin:17-jre-slim
WORKDIR /app

# Copy the application JAR from the build stage
COPY --from=builder /app/build/libs/gradle-wrapper.jar app.jar

# Expose the application's port
EXPOSE 3030

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

