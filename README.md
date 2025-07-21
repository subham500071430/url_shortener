# URL Shortener Project

A full-stack URL shortener application with microservices backend and modern React frontend.

## Architecture

```
url_shortener/
├── url-shortener-service/    # URL shortening microservice (Spring Boot)
├── user-service/            # User management microservice (Spring Boot)  
├── eureka-server/           # Service discovery (Netflix Eureka)
├── frontend/                # React TypeScript frontend (Vite)
└── pom.xml                  # Parent Maven configuration
```

## Services

### Backend Services (Spring Boot)

- **URL Shortener Service** (Port 8080): Core URL shortening functionality
- **User Service**: User management and authentication
- **Eureka Server**: Service discovery and registration

### Frontend (React + TypeScript)

- **React Frontend** (Port 5173): Modern web interface for URL shortening
- Built with Vite for fast development and builds
- TypeScript for type safety
- Responsive design with modern UI

## Getting Started

### Prerequisites

- Java 11+
- Maven 3.6+
- Node.js 16+
- MySQL
- Redis

### Backend Setup

**Important:** Start services in the following order to ensure proper service discovery:

1. **Start MySQL and Redis services**

2. **Build all services:**
   ```bash
   mvn clean install
   ```

3. **Start Eureka Server first:**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```
   
   Wait for Eureka to fully start (you should see "Started EurekaServerApplication" in the logs)
   Access Eureka dashboard at: `http://localhost:8761`

4. **Start URL Shortener Service:**
   ```bash
   cd url-shortener-service
   mvn spring-boot:run
   ```
   
   The service will register with Eureka and be available on port 8080

5. **Start User Service (optional):**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```
   
   The service will register with Eureka and be available on port 8081

### Frontend Setup

1. **Install dependencies:**
   ```bash
   cd frontend
   npm install
   ```

2. **Start development server:**
   ```bash
   npm run dev
   ```

The frontend will be available at `http://localhost:5173`

## Usage

### Web Interface

1. Open `http://localhost:5173` in your browser
2. Enter a long URL in the input field
3. Click "Shorten URL" to generate a short link
4. Copy the shortened URL or test it directly
5. View your URL history in the list below

### API Endpoints

#### URL Shortener Service (Port 8080)

- `POST /bit.ly/shorten/url` - Create shortened URL
  ```json
  {
    "longUrl": "https://example.com/very/long/url"
  }
  ```

- `GET /{shortCode}` - Redirect to original URL

#### Example cURL:

```bash
curl -X POST http://localhost:8080/bit.ly/shorten/url \
  -H "Content-Type: application/json" \
  -d '{"longUrl": "https://www.google.com"}'
```

## Features

### Backend Features

- ✅ URL shortening with unique codes
- ✅ Redis caching for performance
- ✅ MySQL persistence
- ✅ Microservices architecture
- ✅ Service discovery with Eureka
- ✅ CORS support for frontend integration
- ✅ Async processing
- ✅ Error handling and validation

### Frontend Features

- ✅ Modern React UI with TypeScript
- ✅ Responsive design
- ✅ URL validation
- ✅ Copy to clipboard
- ✅ URL history management
- ✅ Error handling and loading states
- ✅ Fast development with Vite

## Technology Stack

### Backend
- **Spring Boot 2.7.18** - Application framework
- **Spring Cloud 2021.0.9** - Microservices toolkit
- **Netflix Eureka** - Service discovery
- **MySQL** - Database
- **Redis** - Caching
- **Maven** - Build tool
- **Java 11** - Programming language

### Frontend
- **React 19** - UI library
- **TypeScript** - Type safety
- **Vite** - Build tool and dev server
- **Axios** - HTTP client
- **CSS3** - Modern styling

## Development

### Backend Development

```bash
# Build all modules
mvn clean install

# Run specific service
cd url-shortener-service
mvn spring-boot:run

# Run tests
mvn test
```

### Frontend Development

```bash
cd frontend

# Development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint
```

## Configuration

### Database Configuration

Update `application.properties` in each service:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=root
```

### Redis Configuration

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

### Eureka Service Discovery Configuration

The project uses Netflix Eureka for service discovery. Each microservice registers with the Eureka server.

#### Eureka Server (Port 8761)

Configuration in `eureka-server/src/main/resources/application.properties`:
```properties
spring.application.name=eureka-server
server.port=8761

# Prevents the server from registering itself as a client
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

#### Client Services Configuration

Both user-service and url-shortener-service are configured as Eureka clients:

```properties
# Service registration with Eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

The services will automatically register with Eureka when started. You can view registered services at: `http://localhost:8761`

### Frontend Configuration

Update API base URL in `frontend/src/services/api.ts`:

```typescript
const API_BASE_URL = 'http://localhost:8080';
```

## Docker Support (Future Enhancement)

The project is structured to support Docker containerization:

```bash
# Build and run with Docker Compose (when added)
docker-compose up -d
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Troubleshooting

### Common Issues

1. **Frontend can't connect to backend:**
   - Ensure backend is running on port 8080
   - Check CORS configuration
   - Verify network connectivity

2. **Database connection issues:**
   - Ensure MySQL is running
   - Check database credentials
   - Verify database exists

3. **Build failures:**
   - Check Java version (11+)
   - Verify Maven configuration
   - Clear Maven cache: `mvn clean install -U`

4. **Frontend build issues:**
   - Check Node.js version (16+)
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules and reinstall

For more specific guidance, see individual service README files.