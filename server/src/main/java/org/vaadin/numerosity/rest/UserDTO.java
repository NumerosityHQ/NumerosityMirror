package org.vaadin.numerosity.rest;

/**
 * Data Transfer Object for user information in REST API operations.
 * Contains user ID and username fields for transferring user data between layers.
 */
public class UserDTO {

    private String userId;
    private String username;
    // Other relevant user data (e.g., email, password - use with caution)

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and setters for other fields
}
