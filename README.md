![numerosity_org_cover](https://github.com/user-attachments/assets/a40c4845-7b31-4a02-ab4f-8676afe3ab30)

# Numerosity - Educational Testing Platform

A comprehensive platform for creating educational testing applications with Firebase integration, REST API, and modern UI.

## Overview

Numerosity provides a robust foundation for building educational testing applications with features including:
- Firebase Authentication and Firestore database integration
- RESTful API for user management
- Multiple quiz modes (Bank, Rush, Zen)
- Modern, responsive web interface
- Analytics and data visualization capabilities
- Secure user authentication and authorization

## Features

### Core Functionality
- **User Authentication**: Secure signup/login with Firebase Auth
- **Database Management**: Firestore integration for storing user data and test results
- **REST API**: Endpoints for user management and data operations
- **Multiple Quiz Modes**: 
  - Bank Mode: Customizable question banks
  - Rush Mode: Timed quizzes for quick practice
  - Zen Mode: Stress-free learning environment
- **Analytics**: Built-in data visualization for tracking performance

### Technical Stack
- **Backend**: Java/Spring Boot with Firebase Admin SDK
- **Frontend**: Vaadin Framework for web application
- **Website**: Modern HTML5/CSS3/JavaScript with responsive design
- **Database**: Firebase Firestore
- **Authentication**: Firebase Authentication

## Getting Started

### Prerequisites
- Java 11 or higher
- Firebase project with Firestore and Authentication enabled
- Maven 3.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/numerosityhq/numerosity.git
   cd numerosity
   ```

2. **Configure Firebase**
   - Create a Firebase project at https://console.firebase.google.com/
   - Enable Firestore Database and Authentication
   - Download your Firebase service account key
   - Place the key file in `server/src/main/resources/` and update `Firebase.json`

3. **Build and Run**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the Application**
   - Vaadin Application: http://localhost:8080
   - Marketing Website: http://localhost:8080/website/

## API Documentation

### User Management Endpoints

#### Create User
```
POST /api/users
Content-Type: application/json

{
  "userId": "string",
  "username": "string"
}

Response: 201 Created
```

#### Get User
```
GET /api/users/{userId}

Response: 200 OK
{
  "userId": "string",
  "username": "string",
  "createdAt": "timestamp"
}
```

### Subsystems

#### LoginHandler
Handles user authentication with Firebase:
- `signup(String email, String password)` - Register new user
- `login(String email, String password)` - Authenticate user
- `logout(String idToken)` - Revoke user session

#### DatabaseHandler
Manages Firestore operations:
- `createUserDocument(String userId, String email)` - Create user profile
- `userExists(String userId)` - Check if user exists
- `incrementCorrect(String userId)` / `incrementWrong(String userId)` - Update stats

#### DataPlotter
Handles data visualization:
- `plotData(String userId, String questionId, boolean correct, long timeTakenMillis, String difficulty, String subject)` - Record attempt

## Project Structure

```
server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/vaadin/numerosity/
│   │   │       ├── Application.java
│   │   │       ├── MainView.java
│   │   │       ├── config/
│   │   │       ├── entity/
│   │   │       ├── Featureset/
│   │   │       │   ├── AppFunctions/
│   │   │       │   │   ├── bank.java
│   │   │       │   │   ├── rush.java
│   │   │       │   │   └── zen.java
│   │   │       │   ├── MathEngine/
│   │   │       │   │   ├── AlgebraOne.java
│   │   │       │   │   ├── AlgebraTwo.java
│   │   │       │   │   ├── Calculus.java
│   │   │       │   │   ├── Geometry.java
│   │   │       │   │   └── Precalculus.java
│   │   │       │   └── Supporter/
│   │   │       │       └── OptionButton.java
│   │   │       ├── repository/
│   │   │       ├── rest/
│   │   │       ├── service/
│   │   │       └── Subsystems/
│   │   │           ├── LoginHandler.java
│   │   │           ├── DatabaseHandler.java
│   │   │           ├── DataPlotter.java
│   │   │           ├── FirebaseHandler.java
│   │   │           ├── ResponseHandler.java
│   │   │           └── UserHandler.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── Firebase.json
│   │   └── frontend/
│   │       ├── index.html
│   │       └── themes/
│   └── test/
│       └── java/
│           └── org/vaadin/numerosity/Subsystems/LoginHandlerTest.java
└── website/
    ├── index.html
    ├── styles.css
    ├── script.js
    ├── physics.js
    └── README.md
```

## Recent Improvements

### Backend Refactoring
- **LoginHandler**: Completely refactored with proper input validation, error handling, and removal of static state
- **MainView**: Updated to use dependency injection for LoginHandler instead of direct Firebase calls
- **Exception Handling**: Added proper try/catch blocks for Firebase operations
- **Security**: Improved input validation and sanitization

### Frontend Modernization
- **Website**: Complete redesign with modern CSS, responsive layout, and improved UX
- **Visual Design**: Updated color scheme, typography, and animations
- **Accessibility**: Improved ARIA labels and semantic HTML
- **Performance**: Optimized CSS and JavaScript

### Testing
- **Unit Tests**: Comprehensive JUnit test suite for LoginHandler covering success cases, error conditions, and edge cases
- **Test Coverage**: Tests for input validation, authentication flows, and error handling

### Documentation
- **README**: Updated with current project status, features, and setup instructions
- **Code Comments**: Improved Javadoc and inline comments throughout refactored code

## Contributing

We welcome contributions to improve Numerosity! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code follows our coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please open an issue on the GitHub repository.

## Acknowledgments

- Firebase team for their excellent backend services
- Vaadin framework for the powerful UI components
- Open source community for various libraries and tools used
