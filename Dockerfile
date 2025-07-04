# Multi-stage build
# Stage 1: Build the application
FROM openjdk:8-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle/ gradle/

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code
COPY src/ src/

# Build the application (skip tests for faster build)
RUN ./gradlew build -x test

# List the built JAR files for debugging
RUN ls -la /app/build/libs/

# Stage 2: Create the runtime image
FROM openjdk:8-jre-alpine

# Set working directory
WORKDIR /app

# Create a non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copy the built JAR from the build stage (using a more flexible approach)
COPY --from=build /app/build/libs/theam-crm-service-*.jar app.jar

# Change ownership of the app directory
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose port 8080
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]