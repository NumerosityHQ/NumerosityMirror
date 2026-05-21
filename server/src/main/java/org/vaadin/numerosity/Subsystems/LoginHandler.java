package org.vaadin.numerosity.Subsystems;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    
    /** Injected DatabaseHandler for user data operations. */
    @Autowired(required = false)
    private DatabaseHandler databaseHandler;

    /** Firebase Auth instance, lazily initialized. */
    private FirebaseAuth firebaseAuth;

    /**
     * Gets the FirebaseAuth instance, initializing it lazily.
     *
     * @return the FirebaseAuth instance
     */
    private FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

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
        // Validate input
        if (email == null || email.trim().isEmpty()) {
            logger.error("Email cannot be null or empty");
            return "Signup failed: Email cannot be null or empty";
        }
        if (password == null || password.trim().isEmpty()) {
            logger.error("Password cannot be null or empty");
            return "Signup failed: Password cannot be null or empty";
        }

        try {
            UserRecord userRecord = getFirebaseAuth().createUser(new UserRecord.CreateRequest()
                    .setEmail(email.trim())
                    .setPassword(password.trim()));
            String userId = userRecord.getUid();

            // Ensure user exists in Firestore (if databaseHandler is available)
            if (databaseHandler != null) {
                if (!databaseHandler.userExists(userId)) {
                    databaseHandler.createUserDocument(userId, email.trim());
                }
            }

            logger.info("User signed up successfully: {}", userId);
            return "Signup successful! User ID: " + userId;
        } catch (FirebaseAuthException e) {
            logger.error("Signup failed for email {}: {}", email, e.getMessage());
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
        // Validate input
        if (email == null || email.trim().isEmpty()) {
            logger.error("Email cannot be null or empty");
            return "Login failed: Email cannot be null or empty";
        }
        if (password == null || password.trim().isEmpty()) {
            logger.error("Password cannot be null or empty");
            return "Login failed: Password cannot be null or empty";
        }

        try {
            UserRecord userRecord = getFirebaseAuth().getUserByEmail(email.trim());
            String userId = userRecord.getUid();

            // Ensure user exists in Firestore (if databaseHandler is available)
            if (databaseHandler != null) {
                if (!databaseHandler.userExists(userId)) {
                    databaseHandler.createUserDocument(userId, email.trim());
                }
            }

            logger.info("User logged in successfully: {}", userId);
            return getFirebaseAuth().createCustomToken(userId);
        } catch (FirebaseAuthException e) {
            logger.error("Login failed for email {}: {}", email, e.getMessage());
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
        // Validate input
        if (idToken == null || idToken.trim().isEmpty()) {
            logger.error("ID token cannot be null or empty");
            return false;
        }

        try {
            FirebaseToken decodedToken = getFirebaseAuth().verifyIdToken(idToken.trim());
            getFirebaseAuth().revokeRefreshTokens(decodedToken.getUid());
            logger.info("User logged out successfully");
            return true;
        } catch (FirebaseAuthException e) {
            logger.error("Logout failed: {}", e.getMessage());
            return false;
        }
    }
}
