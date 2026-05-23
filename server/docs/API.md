# Numerosity API Documentation

## Overview

The Numerosity REST API provides endpoints for:
- User management
- Question retrieval
- Quiz session management

**Base URL**: `http://localhost:8080/api`

## Authentication

All API endpoints require authentication via Firebase ID token.

### Headers

```
Authorization: Bearer <firebase-id-token>
Content-Type: application/json
```

## Endpoints

### Questions API

#### Get All Questions

```
GET /api/questions
```

**Response**:
```json
{
  "questions": [
    {
      "id": "calc1_easy_1",
      "text": "What is the derivative of f(x) = 5x + 3?",
      "latex": "f'(x) = ?",
      "options": [
        {"id": "a", "text": "5x + 3"},
        {"id": "b", "text": "5"},
        {"id": "c", "text": "3"},
        {"id": "d", "text": "5x"}
      ],
      "correct_option_id": "b",
      "difficulty": "easy",
      "category": "Calculus 1",
      "subcategory": "Derivatives",
      "tags": ["calculus", "derivative", "linear"],
      "explanation": "The derivative of a linear function ax + b is simply the coefficient a."
    }
  ]
}
```

#### Get Questions by Category

```
GET /api/questions/category/{category}
```

**Parameters**:
- `category` - One of: `Calculus 1`, `Geometry`, `Algebra`, `Trigonometry`, `Statistics`

**Example**:
```bash
curl -X GET http://localhost:8080/api/questions/category/Algebra
```

#### Get Questions by Difficulty

```
GET /api/questions/difficulty/{difficulty}
```

**Parameters**:
- `difficulty` - One of: `easy`, `medium`, `hard`

**Example**:
```bash
curl -X GET http://localhost:8080/api/questions/difficulty/hard
```

#### Get Random Question

```
GET /api/questions/random
```

**Query Parameters**:
- `category` (optional) - Filter by category
- `difficulty` (optional) - Filter by difficulty

**Example**:
```bash
curl -X GET "http://localhost:8080/api/questions/random?category=Geometry&difficulty=medium"
```

### Users API

#### Create User

```
POST /api/users
```

**Request Body**:
```json
{
  "email": "user@example.com",
  "username": "user123"
}
```

**Response**:
```json
{
  "userId": "abc123",
  "email": "user@example.com",
  "username": "user123",
  "createdAt": "2025-03-30T19:40:00Z"
}
```

#### Get User by ID

```
GET /api/users/{userId}
```

**Response**:
```json
{
  "userId": "abc123",
  "email": "user@example.com",
  "username": "user123",
  "stats": {
    "totalQuestions": 150,
    "correctAnswers": 120,
    "streak": 15
  }
}
```

#### Update User

```
PUT /api/users/{userId}
```

**Request Body**:
```json
{
  "username": "newUsername"
}
```

## Error Responses

### 400 Bad Request

```json
{
  "error": "Invalid request",
  "message": "Email is required",
  "timestamp": "2025-03-30T19:40:00Z"
}
```

### 401 Unauthorized

```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token",
  "timestamp": "2025-03-30T19:40:00Z"
}
```

### 404 Not Found

```json
{
  "error": "Not Found",
  "message": "Question not found with id: xyz",
  "timestamp": "2025-03-30T19:40:00Z"
}
```

### 500 Internal Server Error

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "timestamp": "2025-03-30T19:40:00Z"
}
```

## Code Examples

### JavaScript (Fetch)

```javascript
// Get questions by category
async function getQuestions(category) {
  const response = await fetch(`/api/questions/category/${category}`, {
    headers: {
      'Authorization': `Bearer ${firebaseToken}`,
      'Content-Type': 'application/json'
    }
  });
  return await response.json();
}

// Create a new user
async function createUser(email, username) {
  const response = await fetch('/api/users', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${firebaseToken}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, username })
  });
  return await response.json();
}
```

### Java (RestTemplate)

```java
@RestController
public class QuestionClient {
    
    private final RestTemplate restTemplate;
    
    public List<Question> getQuestionsByCategory(String category) {
        String url = "http://localhost:8080/api/questions/category/" + category;
        ResponseEntity<QuestionResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            QuestionResponse.class
        );
        return response.getBody().getQuestions();
    }
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(firebaseToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
```

### Python (Requests)

```python
import requests

def get_questions(category, token):
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }
    response = requests.get(
        f'http://localhost:8080/api/questions/category/{category}',
        headers=headers
    )
    return response.json()
```

## Rate Limiting

API requests are limited to:
- **100 requests per minute** per IP address
- **1000 requests per hour** per authenticated user

Rate limit headers are included in all responses:
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1617123456
```

## Versioning

The API uses URL versioning. Current version: `v1`

```
http://localhost:8080/api/v1/questions