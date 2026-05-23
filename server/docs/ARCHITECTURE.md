# Numerosity Architecture Overview

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Client (Browser)                          │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                    Vaadin Flow UI                          │  │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  │
│  │  │   MainView   │  │   bank       │  │   rush/zen       │  │  │
│  │  │  (Modern UI) │  │  (Quiz Mode) │  │  (Game Modes)    │  │  │
│  │  └──────────────┘  └──────────────┘  └──────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
└──────────────────────────────┬────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Spring Boot Application                      │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                   REST Controllers                         │  │
│  │  ┌──────────────────┐  ┌──────────────────────────────┐  │  │
│  │  │ QuestionController│  │       UserController         │  │  │
│  │  └──────────────────┘  └──────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                     Services                               │  │
│  │  ┌──────────────────┐  ┌──────────────────────────────┐  │  │
│  │  │   UserService    │  │    QuestionService           │  │  │
│  │  └──────────────────┘  └──────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                   Subsystems                               │  │
│  │  ┌──────────────────┐  ┌──────────────────────────────┐  │  │
│  │  │  LoginHandler    │  │   QuestionContentLoader      │  │  │
│  │  │  (Firebase Auth) │  │   (JSON Data Loading)        │  │  │
│  │  └──────────────────┘  └──────────────────────────────┘  │  │
└──────────────────────────────┬────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Data Layer                                    │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                   JSON Database                              │  │
│  │  ┌──────────────────────────────────────────────────────┐  │  │
│  │  │  questions-comprehensive.json                          │  │  │
│  │  │  - Calculus 1 (4 questions)                           │  │  │
│  │  │  - Geometry (4 questions)                             │  │  │
│  │  │  - Algebra (4 questions)                              │  │  │
│  │  │  - Trigonometry (4 questions)                         │  │  │
│  │  │  - Statistics (4 questions)                           │  │  │
│  │  └──────────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Component Diagram

### Core Components

1. **MainViewModern** - Main application view with:
   - Header with navigation
   - Theme toggle (dark/light mode)
   - Login/Signup dialog
   - Responsive layout

2. **ThemeManager** - Theme management service:
   - Light/Dark theme switching
   - Theme variant support (Material, Lumo)
   - Smooth CSS transitions

3. **QuestionSeeder** - Database seeding:
   - Loads questions from JSON
   - Categorizes by subject and difficulty
   - Provides filtering capabilities

4. **LoginHandler** - Authentication:
   - Firebase Authentication integration
   - User signup and login
   - Token management

### Data Flow

```
User Request → Vaadin UI → Controller → Service → Repository → JSON
                                                    ↑
                                            QuestionSeeder
                                                    ↓
                                            questions.json
```

## Package Structure

```
org.vaadin.numerosity
├── Application.java           # Spring Boot entry point
├── MainView.java              # Original main view
├── config/                    # Configuration classes
│   ├── ApplicationConfig.java
│   └── FirestoreAvailableCondition.java
├── entity/                    # Data entities
│   └── User.java
├── Featureset/                # Feature modules
│   ├── AppFunctions/          # View components
│   │   ├── bank.java          # Practice mode
│   │   ├── rush.java          # Rush mode
│   │   └── zen.java           # Zen mode
│   ├── MathEngine/            # Math problem generators
│   │   ├── AlgebraOne.java
│   │   ├── AlgebraTwo.java
│   │   ├── Calculus.java
│   │   ├── Geometry.java
│   │   └── Precalculus.java
│   └── Supporter/             # UI components
│       └── OptionButton.java
├── repository/                # Data access
│   ├── UserRepository.java
│   ├── FsUserRepository.java
│   └── exception/
│       └── DbException.java
├── rest/                      # REST API
│   ├── QuestionRestController.java
│   ├── UserRestController.java
│   └── UserDTO.java
├── service/                   # Business logic
│   └── UserService.java
├── Subsystems/                # Core subsystems
│   ├── LoginHandler.java
│   ├── DatabaseHandler.java
│   ├── FirebaseHandler.java
│   ├── QuestionContentLoader.java
│   ├── QuestionSeeder.java
│   └── ...
└── ui/                        # Modern UI components
    ├── ThemeManager.java
    └── views/
        └── MainViewModern.java
```

## Technology Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot 3.4.3 |
| Frontend | Vaadin 24.6.6 |
| Authentication | Firebase Auth |
| Build | Maven |
| Java Version | Java 17 |
| JSON Processing | Jackson |

## Design Patterns

1. **MVC Pattern** - Separation of concerns
2. **Dependency Injection** - Spring's @Autowired
3. **Command Pattern** - Button click handlers
4. **Factory Pattern** - Question generation
5. **Singleton Pattern** - ThemeManager