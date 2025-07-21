# User Service Dockerfile - Multi-stage build
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Maven wrapper and parent pom
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY pom.xml ./

# Copy all module poms
COPY eureka-server/pom.xml eureka-server/
COPY user-service/pom.xml user-service/
COPY url-shortener-service/pom.xml url-shortener-service/

# Download dependencies for better caching
RUN chmod +x mvnw && ./mvnw dependency:go-offline -N
RUN ./mvnw dependency:go-offline -pl user-service

# Copy source code
COPY user-service/src user-service/src

# Build the application
RUN ./mvnw clean package -pl user-service -DskipTests

# Production stage
FROM openjdk:17-jre-slim

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR file
COPY --from=build /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]