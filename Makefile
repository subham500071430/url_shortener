# Makefile for URL Shortener Docker operations

.PHONY: help build up down logs clean test-build dev prod status

# Default target
help:
	@echo "URL Shortener Docker Operations"
	@echo "==============================="
	@echo "Available targets:"
	@echo "  build      - Build all Docker images"
	@echo "  up         - Start all services with docker-compose"
	@echo "  dev        - Start services in development mode"
	@echo "  down       - Stop all services"
	@echo "  logs       - Show logs from all services"
	@echo "  clean      - Remove containers, images, and volumes"
	@echo "  test-build - Test building individual services"
	@echo "  status     - Show status of all containers"
	@echo "  prod       - Build and run for production"

# Build all images
build:
	@echo "Building all Docker images..."
	docker compose build

# Start all services
up:
	@echo "Starting all services..."
	docker compose up -d
	@echo "Services started! Check status with 'make status'"
	@echo "Access the application at:"
	@echo "  Frontend: http://localhost:80"
	@echo "  Eureka Server: http://localhost:8761"
	@echo "  User Service: http://localhost:8081"
	@echo "  URL Shortener: http://localhost:8080"

# Start in development mode
dev:
	@echo "Starting services in development mode..."
	docker compose -f docker-compose.yml -f docker-compose.dev.yml up -d

# Stop all services
down:
	@echo "Stopping all services..."
	docker compose down

# Show logs
logs:
	docker compose logs -f

# Clean up everything
clean:
	@echo "Cleaning up Docker resources..."
	docker compose down -v --rmi all --remove-orphans
	docker system prune -f

# Test building individual services
test-build:
	@echo "Testing frontend build..."
	docker build -t test-frontend ./frontend
	@echo "Testing eureka-server build..."
	docker build -f eureka-server/Dockerfile -t test-eureka .
	@echo "All builds successful!"

# Show container status
status:
	@echo "Container Status:"
	@echo "=================="
	docker compose ps

# Production deployment
prod: build
	@echo "Starting production deployment..."
	docker compose up -d
	@echo "Production deployment completed!"
	@echo "Monitor with: make logs"