package org.vaadin.numerosity.rest;

public class UserDTO {

    private String userId;
    private String username;
    // Other relevant user data (e.g., email, password - use with caution)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and setters for other fields
}
