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

- Java 17+
- Maven 3.6+
- Node.js 16+
- MySQL
- Redis

### Backend Setup

1. **Start MySQL and Redis services**

2. **Build all services:**
   ```bash
   mvn clean install
   ```

3. **Start Eureka Server:**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```

4. **Start URL Shortener Service:**
   ```bash
   cd url-shortener-service
   mvn spring-boot:run
   ```

5. **Start User Service (optional):**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```

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
- **Spring Boot 3.3.3** - Application framework
- **Spring Cloud** - Microservices toolkit
- **Netflix Eureka** - Service discovery
- **MySQL** - Database
- **Redis** - Caching
- **Maven** - Build tool
- **Java 17** - Programming language

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
   - Check Java version (17+)
   - Verify Maven configuration
   - Clear Maven cache: `mvn clean install -U`

4. **Frontend build issues:**
   - Check Node.js version (16+)
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules and reinstall

For more specific guidance, see individual service README files.