# Numerosity Demo Application

A comprehensive demo application showcasing a modern math quiz platform built with Vaadin and Spring Boot.

## Features

### Math Question Database
- **20+ questions** across 5 categories:
  - Calculus 1 (4 questions)
  - Geometry (4 questions)
  - Algebra (4 questions)
  - Trigonometry (4 questions)
  - Statistics (4 questions)
- **3 difficulty levels**: Easy, Medium, Hard
- Rich metadata including explanations and LaTeX support

### Modern UI
- **Dark/Light mode** toggle with smooth transitions
- **Responsive design** optimized for desktop and mobile
- **Minimalist aesthetic** with consistent spacing and typography
- **Interactive elements** with hover and focus states

### Comprehensive Documentation
- Architecture overview with component diagrams
- Step-by-step setup guide
- API documentation with example requests/responses
- Troubleshooting section

### Docker Support
- Multi-stage Docker build
- Docker Compose for easy deployment
- Health checks and proper configuration

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Firebase account (for authentication)

### Run with Maven

```bash
# Clone and navigate to server directory
cd server

# Build the application
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

### Run with Docker

```bash
# Build and run with Docker Compose
docker-compose up -d

# Access the application
open http://localhost:8080
```

## Project Structure

```
server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/vaadin/numerosity/
│   │   │       ├── Featureset/           # Quiz modes (bank, rush, zen)
│   │   │       ├── Subsystems/           # Core services
│   │   │       ├── rest/                 # REST API controllers
│   │   │       ├── service/              # Business logic
│   │   │       ├── ui/                   # Modern UI components
│   │   │       └── repository/           # Data access
│   │   └── resources/
│   │       └── Firebase.json             # Firebase config
│   └── test/                             # Test files
├── Database/
│   └── Bank/
│       ├── questions.json                # Original questions
│       └── questions-comprehensive.json  # New comprehensive dataset
├── docs/                                 # Documentation
│   ├── ARCHITECTURE.md
│   ├── SETUP.md
│   └── API.md
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/questions` | GET | Get all questions |
| `/api/questions/category/{category}` | GET | Get questions by category |
| `/api/questions/difficulty/{difficulty}` | GET | Get questions by difficulty |
| `/api/questions/random` | GET | Get a random question |
| `/api/users` | POST | Create a new user |
| `/api/users/{userId}` | GET | Get user by ID |

## Views

| Route | View | Description |
|-------|------|-------------|
| `/` | MainView | Original main view |
| `/modern` | MainViewModern | Modern UI with theme toggle |
| `/bank` | bank | Practice mode |
| `/rush` | rush | Timed quiz mode |
| `/zen` | zen | Relaxed learning mode |

## Testing

```bash
# Run all tests
./mvnw test

# Run specific test
./mvnw test -Dtest=QuestionSeederTest

# Run with coverage
./mvnw test jacoco:report
```

## Building for Production

```bash
# Create executable JAR
./mvnw clean package

# The JAR will be at:
# target/numerosity-1.0.0.jar

# Run the JAR
java -jar target/numerosity-1.0.0.jar
```

## Configuration

### Application Properties

```properties
# Server
server.port=8080

# Vaadin
vaadin.productionMode=true

# Firebase
firebase.config.path=classpath:Firebase.json
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Server port | 8080 |
| `FIREBASE_CONFIG_PATH` | Firebase config path | classpath:Firebase.json |

## Documentation

- [Architecture](docs/ARCHITECTURE.md) - System design and components
- [Setup Guide](docs/SETUP.md) - Installation and configuration
- [API Documentation](docs/API.md) - REST API reference

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.