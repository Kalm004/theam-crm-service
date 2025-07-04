# Docker Setup for Theam CRM Service

This project includes Docker configuration to build and run the Spring Boot application in containers.

## Files Created

- `Dockerfile` - Multi-stage build configuration for the application
- `docker-compose.yml` - Complete setup with PostgreSQL database
- `.dockerignore` - Optimizes Docker build by excluding unnecessary files

## Usage

### Option 1: Using Docker Compose (Recommended)

Run the entire application stack (app + PostgreSQL database):

```bash
# Build and start all services
docker-compose up --build

# Start services in background
docker-compose up -d --build

# View logs
docker-compose logs -f theam-crm-service

# Stop all services
docker-compose down

# Stop and remove volumes (clears database data)
docker-compose down -v
```

### Option 2: Using Docker only

Build and run just the application container:

```bash
# Build the image
docker build -t theam-crm-service .

# Run the container
docker run -p 8080:8080 theam-crm-service

# Run with custom environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  theam-crm-service
```

## Application Access

- **Application URL**: http://localhost:8080
- **Swagger UI** (if enabled): http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

## Database Access (when using docker-compose)

- **Host**: localhost
- **Port**: 5432
- **Database**: theamcrm
- **Username**: theamcrm
- **Password**: theamcrm123

## Configuration

### Environment Variables

The application supports these environment variables:

- `SPRING_PROFILES_ACTIVE` - Active Spring profile (default: none)
- `SPRING_DATASOURCE_URL` - Database URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password

### Custom Configuration

To use custom configuration:

1. Create an `application-docker.yml` file in `src/main/resources/`
2. Set `SPRING_PROFILES_ACTIVE=docker` in docker-compose.yml
3. Rebuild the image

## Troubleshooting

### Build Issues

```bash
# Clean build
docker-compose down
docker system prune -f
docker-compose up --build --force-recreate
```

### Database Connection Issues

```bash
# Check if postgres is running
docker-compose ps

# Check postgres logs
docker-compose logs postgres

# Reset database
docker-compose down -v
docker-compose up -d postgres
```

### Application Logs

```bash
# Follow application logs
docker-compose logs -f theam-crm-service

# Check container status
docker-compose ps
```