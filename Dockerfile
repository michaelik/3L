# Use a JDK 21 base image for building
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Install Gradle
RUN apt-get update && apt-get install -y curl unzip && \
    curl -s https://downloads.gradle-dn.com/distributions/gradle-7.6-bin.zip -o gradle.zip && \
    unzip gradle.zip -d /opt/gradle && \
    rm gradle.zip
ENV PATH="/opt/gradle/gradle-7.6/bin:${PATH}"

# Copy project files and build
COPY . .
RUN gradle clean build -x test

# Use a lightweight JDK runtime for the final image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the application JAR from the build stage
COPY --from=builder /app/build/libs/gradle-wrapper.jar app.jar

# Expose the application's port
EXPOSE 3030

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]



