# URL Shortener - Docker Setup

This document describes how to run the URL Shortener application using Docker containers.

## Architecture

The application consists of the following services:

- **Frontend**: React + Vite application served by nginx (port 80)
- **Eureka Server**: Service discovery server (port 8761)
- **User Service**: User management service (port 8081)
- **URL Shortener Service**: Main URL shortening service (port 8080)
- **MySQL**: Database for both user and URL data (port 3306)
- **Redis**: Caching service for the URL shortener (port 6379)

## Quick Start

### Using Docker Compose (Recommended)

1. **Start all services:**
   ```bash
   docker-compose up -d
   ```

2. **View logs:**
   ```bash
   docker-compose logs -f
   ```

3. **Stop all services:**
   ```bash
   docker-compose down
   ```

4. **Remove all data (including database):**
   ```bash
   docker-compose down -v
   ```

### Using Single Dockerfile

Alternatively, you can build a single container with all services:

1. **Build the image:**
   ```bash
   docker build -t url-shortener-app .
   ```

2. **Run with external dependencies:**
   ```bash
   # Start MySQL and Redis first
   docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:8.0
   docker run -d --name redis -p 6379:6379 redis:7-alpine
   
   # Start the application
   docker run -d --name url-shortener -p 80:80 -p 8080:8080 -p 8081:8081 -p 8761:8761 url-shortener-app
   ```

## Development Setup

For development with hot reloading:

```bash
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
```

This enables:
- Debug ports for Java services (5005, 5006, 5007)
- Hot reloading for frontend
- Development profiles for Spring Boot services

## Service URLs

After startup, the services will be available at:

- **Frontend**: http://localhost:80
- **Eureka Dashboard**: http://localhost:8761
- **User Service API**: http://localhost:8081
- **URL Shortener API**: http://localhost:8080

## Environment Variables

### Production Environment Variables

The following environment variables are configured for production:

#### User Service
- `SPRING_DATASOURCE_URL`: jdbc:mysql://mysql:3306/user
- `SPRING_DATASOURCE_USERNAME`: root
- `SPRING_DATASOURCE_PASSWORD`: root
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE`: http://eureka-server:8761/eureka/

#### URL Shortener Service
- `SPRING_DATASOURCE_URL`: jdbc:mysql://mysql:3306/url_shortener
- `SPRING_DATASOURCE_USERNAME`: root
- `SPRING_DATASOURCE_PASSWORD`: root
- `SPRING_REDIS_HOST`: redis
- `SPRING_REDIS_PORT`: 6379
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE`: http://eureka-server:8761/eureka/

### Custom Configuration

You can override environment variables by creating a `.env` file:

```env
# Database configuration
MYSQL_ROOT_PASSWORD=your_password
MYSQL_DATABASE=your_database

# Redis configuration
REDIS_PASSWORD=your_redis_password

# Application configuration
SPRING_PROFILES_ACTIVE=production
```

## Health Checks

All services include health checks:

- **Frontend**: HTTP GET to http://localhost:80/
- **Java Services**: HTTP GET to http://localhost:<port>/actuator/health
- **MySQL**: `mysqladmin ping`
- **Redis**: `redis-cli ping`

## Troubleshooting

### Service Dependencies

Services start in the following order:
1. MySQL and Redis (databases)
2. Eureka Server (service discovery)
3. User Service and URL Shortener Service (business services)
4. Frontend (web interface)

### Common Issues

1. **Services not starting**: Check that required ports are available
2. **Database connection issues**: Ensure MySQL is healthy before services start
3. **Service discovery issues**: Verify Eureka Server is running and accessible

### Viewing Logs

```bash
# All services
docker-compose logs

# Specific service
docker-compose logs eureka-server
docker-compose logs user-service
docker-compose logs url-shortener-service
docker-compose logs frontend

# Follow logs in real-time
docker-compose logs -f <service-name>
```

### Debugging

For debugging Java services in development mode:

1. Start with development compose: `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up`
2. Connect your IDE debugger to:
   - Eureka Server: localhost:5005
   - User Service: localhost:5006
   - URL Shortener Service: localhost:5007

## Production Deployment

For production deployment:

1. **Set appropriate environment variables**
2. **Use production database credentials**
3. **Configure proper networking and security**
4. **Set up monitoring and logging**
5. **Use Docker secrets for sensitive data**

Example production docker-compose.prod.yml:

```yaml
version: '3.8'
services:
  mysql:
    environment:
      MYSQL_ROOT_PASSWORD_FILE: /run/secrets/mysql_root_password
    secrets:
      - mysql_root_password

secrets:
  mysql_root_password:
    external: true
```

## Building Individual Services

To build individual service images:

```bash
# Frontend
docker build -t url-shortener-frontend ./frontend

# Eureka Server
docker build -f eureka-server/Dockerfile -t url-shortener-eureka .

# User Service
docker build -f user-service/Dockerfile -t url-shortener-user .

# URL Shortener Service
docker build -f url-shortener-service/Dockerfile -t url-shortener-api .
```