package org.vaadin.numerosity.Subsystems;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

/**
 * Service class for handling user authentication via Firebase Auth.
 * Manages signup, login, and logout operations.
 */
@Service
public class LoginHandler {

    /** Injected DatabaseHandler for user data operations. */
    @Autowired
    private DatabaseHandler databaseHandler;

    /** Firebase Auth instance. */
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    /** The last created user record. */
    public static UserRecord createdUser = null; // Initialize userRecord to null
    /** The current user's email. */
    public String userEmail = null;

    /*
     * Button loginSubmitButton = new Button("Login", e -> {
     * try {
     * UserRecord userRecord = firebaseAuth.getUserByEmail(emailField.getValue());
     * userEmail = emailField.getValue();
     * // Add your password verification logic here
     * Notification.show("Login successful! Welcome, " + userRecord.getEmail());
     * dialog.close();
     * } catch (FirebaseAuthException ex) {
     * Notification.show("Login failed: " + ex.getMessage());
     * }
     * });
     */
    /**
     * Signs up a new user with the given email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return success message or error message
     * @throws ExecutionException if database operation fails
     * @throws InterruptedException if database operation is interrupted
     */
    public String signup(String email, String password) throws ExecutionException, InterruptedException {
        try {
            UserRecord userRecord = firebaseAuth.createUser(new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password));
            createdUser = userRecord; // Store the created user record
            String userId = userRecord.getUid();

            // Ensure user exists in Firestore
            if (!databaseHandler.userExists(userId)) {
                databaseHandler.createUserDocument(userId, email);
            }

            return "Signup successful! User ID: " + userId;
        } catch (FirebaseAuthException e) {
            return "Signup failed: " + e.getMessage();
        }
    }
    
    /**
     * Logs in a user with the given email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return custom token or error message
     * @throws ExecutionException if database operation fails
     * @throws InterruptedException if database operation is interrupted
     */
    public String login(String email, String password) throws ExecutionException, InterruptedException {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            String userId = userRecord.getUid();

            // Ensure user exists in Firestore
            if (!databaseHandler.userExists(userId)) {
                databaseHandler.createUserDocument(userId, email);
            }

            return FirebaseAuth.getInstance().createCustomToken(userId);
        } catch (FirebaseAuthException e) {
            return "Login failed: " + e.getMessage();
        }
    }

    /**
     * Logs out a user by revoking their refresh tokens.
     *
     * @param idToken the user's ID token
     * @return true if logout successful, false otherwise
     */
    public boolean logout(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            FirebaseAuth.getInstance().revokeRefreshTokens(decodedToken.getUid());
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }
}
