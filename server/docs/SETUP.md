# Numerosity Setup Guide

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+** (or use the included Maven wrapper)
- **Node.js 18+** (for Vaadin frontend)
- **Firebase Account** (for authentication)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/numerosity.git
cd numerosity/server
```

### 2. Configure Firebase

1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Download the `Firebase.json` file
3. Place it in `src/main/resources/Firebase.json`

```json
{
  "type": "service_account",
  "project_id": "your-project-id",
  "private_key_id": "...",
  "private_key": "-----BEGIN PRIVATE KEY-----\n...\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-...@your-project.iam.gserviceaccount.com",
  "client_id": "...",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token"
}
```

### 3. Build the Application

```bash
# Using Maven wrapper (recommended)
./mvnw clean install

# Or using system Maven
mvn clean install
```

### 4. Run the Application

```bash
# Development mode
./mvnw spring-boot:run

# Or run the JAR
java -jar target/numerosity-1.0.0.jar
```

### 5. Access the Application

Open your browser and navigate to:
- **Main View**: http://localhost:8080/
- **Modern View**: http://localhost:8080/modern
- **API Docs**: http://localhost:8080/swagger-ui.html (if configured)

## Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8080
server.servlet.context-path=/

# Vaadin configuration
vaadin.productionMode=false

# Firebase configuration
firebase.config.path=classpath:Firebase.json
```

### Environment Variables

```bash
# Set Firebase credentials path
export FIREBASE_CONFIG_PATH=/path/to/Firebase.json

# Set server port
export SERVER_PORT=8080
```

## Development Setup

### IDE Configuration

#### IntelliJ IDEA

1. Open the project as a Maven project
2. Enable annotation processing: `Settings → Build → Compiler → Annotation Processors`
3. Install Lombok plugin if using Lombok

#### VS Code

1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Install Vaadin Snippets extension

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=LoginHandlerTest

# Run with coverage
./mvnw test jacoco:report
```

## Docker Deployment

### Build Docker Image

```bash
# Build JAR first
./mvnw clean package

# Build Docker image
docker build -t numerosity:latest .
```

### Run with Docker

```bash
docker run -d \
  -p 8080:8080 \
  -v /path/to/Firebase.json:/app/config/Firebase.json \
  -e FIREBASE_CONFIG_PATH=/app/config/Firebase.json \
  --name numerosity \
  numerosity:latest
```

## Database Seeding

The application includes a comprehensive question database. To seed:

```java
// The QuestionSeeder runs automatically on startup
// Or manually trigger via:
QuestionSeeder seeder = new QuestionSeeder();
seeder.seedQuestions();
```

## Troubleshooting

### Common Issues

#### Port Already in Use

```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

#### Firebase Authentication Errors

1. Verify `Firebase.json` is valid
2. Check Firebase project has Authentication enabled
3. Ensure service account has proper permissions

#### Vaadin Frontend Issues

```bash
# Clean frontend cache
./mvnw vaadin:clean-frontend

# Rebuild frontend
./mvnw vaadin:prepare-frontend
```

## Next Steps

1. Review the [Architecture Documentation](ARCHITECTURE.md)
2. Check the [API Documentation](API.md)
3. Read the [Testing Guide](TESTING.md)