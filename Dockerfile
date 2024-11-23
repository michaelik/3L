# Use a JDK 21 base image for building
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy project files and build
COPY . .
RUN gradle clean build -x test

# Copy the application JAR from the build stage
COPY --from=builder /app/build/libs/gradle-wrapper.jar app.jar

# Expose the application's port
EXPOSE 3030

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]



