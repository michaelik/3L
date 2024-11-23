# Use a JDK 17 base image for building
FROM gradle:7.6-jdk17-alpine AS builder
WORKDIR /app

# Copy project files and build
COPY . .
RUN gradle clean build -x test

# Use a runtime JDK base image for the final image
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copy the application JAR from the build stage
COPY --from=builder /app/build/libs/gradle-wrapper.jar app.jar

# Expose the application's port
EXPOSE 3030

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]



