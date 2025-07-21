# Root Dockerfile for the entire URL Shortener application
# This builds all services in a single image (alternative to docker-compose)

# Multi-stage build
FROM node:18-alpine AS frontend-build

WORKDIR /app/frontend

# Copy frontend package files
COPY frontend/package*.json ./

# Install frontend dependencies
RUN npm ci --only=production

# Copy frontend source
COPY frontend/ ./

# Build frontend
RUN npm run build

# Java build stage
FROM openjdk:17-jdk-slim AS java-build

WORKDIR /app

# Copy Maven wrapper and configuration
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY pom.xml ./

# Copy all service poms
COPY eureka-server/pom.xml eureka-server/
COPY user-service/pom.xml user-service/
COPY url-shortener-service/pom.xml url-shortener-service/

# Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY eureka-server/src eureka-server/src
COPY user-service/src user-service/src
COPY url-shortener-service/src url-shortener-service/src

# Build all services
RUN ./mvnw clean package -DskipTests

# Final runtime stage
FROM openjdk:17-jre-slim

WORKDIR /app

# Install nginx and curl
RUN apt-get update && \
    apt-get install -y nginx curl && \
    rm -rf /var/lib/apt/lists/*

# Copy built JAR files
COPY --from=java-build /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar ./eureka-server.jar
COPY --from=java-build /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar ./user-service.jar
COPY --from=java-build /app/url-shortener-service/target/url-shortener-service-0.0.1-SNAPSHOT.jar ./url-shortener-service.jar

# Copy built frontend
COPY --from=frontend-build /app/frontend/dist /var/www/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy startup script
COPY start-all.sh ./
RUN chmod +x start-all.sh

# Expose all necessary ports
EXPOSE 80 8080 8081 8761

# Health check for the main application
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=3 \
  CMD curl -f http://localhost:80/ && \
      curl -f http://localhost:8761/actuator/health && \
      curl -f http://localhost:8080/actuator/health && \
      curl -f http://localhost:8081/actuator/health || exit 1

# Start all services
CMD ["./start-all.sh"]