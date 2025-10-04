# URL Shortener Service Dockerfile - Multi-stage build
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
COPY api-gateway/pom.xml api-gateway/

# Copy source code
COPY url-shortener-service/src url-shortener-service/src

# Build the application
RUN chmod +x mvnw && ./mvnw clean package -pl url-shortener-service -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR file
COPY --from=build /app/url-shortener-service/target/url-shortener-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/ || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]