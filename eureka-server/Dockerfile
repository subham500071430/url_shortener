# Eureka Server Dockerfile - Multi-stage build
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

# Copy source code
COPY eureka-server/src eureka-server/src

# Build the application
RUN chmod +x mvnw && ./mvnw clean package -pl eureka-server -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the executable fat JAR file built by spring-boot-maven-plugin
# This JAR contains all dependencies and has Main-Class: org.springframework.boot.loader.JarLauncher
# The plugin creates a single executable JAR that can be run with 'java -jar'
COPY --from=build /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8761

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8761/ || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]