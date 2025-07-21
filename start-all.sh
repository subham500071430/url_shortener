#!/bin/bash

# Startup script for all services in the URL Shortener application

echo "Starting URL Shortener Application..."

# Start nginx for frontend
echo "Starting nginx for frontend..."
nginx &

# Wait a moment for nginx to start
sleep 5

# Start Eureka Server first (service discovery)
echo "Starting Eureka Server..."
java -jar eureka-server.jar --spring.profiles.active=docker &
EUREKA_PID=$!

# Wait for Eureka to be ready
echo "Waiting for Eureka Server to be ready..."
until curl -f http://localhost:8761/ >/dev/null 2>&1; do
    echo "Waiting for Eureka Server..."
    sleep 10
done
echo "Eureka Server is ready!"

# Start User Service
echo "Starting User Service..."
java -jar user-service.jar \
    --spring.profiles.active=docker \
    --spring.datasource.url=jdbc:mysql://localhost:3306/user \
    --eureka.client.service-url.defaultZone=http://localhost:8761/eureka/ &
USER_SERVICE_PID=$!

# Start URL Shortener Service
echo "Starting URL Shortener Service..."
java -jar url-shortener-service.jar \
    --spring.profiles.active=docker \
    --spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener \
    --spring.redis.host=localhost \
    --eureka.client.service-url.defaultZone=http://localhost:8761/eureka/ &
URL_SERVICE_PID=$!

# Function to handle shutdown
shutdown() {
    echo "Shutting down all services..."
    kill $EUREKA_PID $USER_SERVICE_PID $URL_SERVICE_PID 2>/dev/null
    nginx -s quit
    exit 0
}

# Trap signals for graceful shutdown
trap shutdown SIGINT SIGTERM

echo "All services started! Application is running."
echo "Frontend: http://localhost:80"
echo "Eureka Server: http://localhost:8761"
echo "User Service: http://localhost:8081"
echo "URL Shortener Service: http://localhost:8080"

# Keep the script running
wait