# User Service Dockerfile - Multi-stage build
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Maven wrapper and parent pom
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY pom.xml ./

# Copy all module poms
COPY eureka-server/pom.xml eureka-server/
COPY auth-service/pom.xml auth-service/
COPY url-shortener-service/pom.xml url-shortener-service/

# Copy source code
COPY auth-service/src auth-service/src

# Build the application
RUN chmod +x mvnw && ./mvnw clean package -pl auth-service -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR file
COPY --from=build /app/auth-service/target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8081/ || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]