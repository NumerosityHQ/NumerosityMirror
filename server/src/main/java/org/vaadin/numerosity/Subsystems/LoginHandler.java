package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.config.FirebaseConfigurationSupport;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

/**
 * Service class for handling user authentication via Firebase Auth.
 */
@Service
public class LoginHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    private final UserManager userManager;

    @Autowired(required = false)
    private DatabaseHandler databaseHandler;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Value("${firebase.project.id}")
    private String projectId;

    private FirebaseAuth firebaseAuth;

    public LoginHandler(UserManager userManager) {
        this.userManager = userManager;
    }

    private FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public String signup(String email, String password) throws ExecutionException, InterruptedException {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Email cannot be null or empty");
            return "Signup failed: Email cannot be null or empty";
        }
        if (password == null || password.trim().isEmpty()) {
            logger.error("Password cannot be null or empty");
            return "Signup failed: Password cannot be null or empty";
        }

        if (FirebaseConfigurationSupport.isDemoMode(credentialsPath, projectId)) {
            try {
                String userId = userManager.signup(email.trim(), password.trim(), "demo");
                logger.info("User signed up successfully in demo mode: {}", userId);
                return "Signup successful! User ID: " + userId;
            } catch (IOException e) {
                logger.error("Demo signup failed for email {}: {}", email, e.getMessage());
                return "Signup failed: " + e.getMessage();
            }
        }

        try {
            UserRecord userRecord = getFirebaseAuth().createUser(new UserRecord.CreateRequest()
                    .setEmail(email.trim())
                    .setPassword(password.trim()));
            String userId = userRecord.getUid();

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

    public String login(String email, String password) throws ExecutionException, InterruptedException {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Email cannot be null or empty");
            return "Login failed: Email cannot be null or empty";
        }
        if (password == null || password.trim().isEmpty()) {
            logger.error("Password cannot be null or empty");
            return "Login failed: Password cannot be null or empty";
        }

        if (FirebaseConfigurationSupport.isDemoMode(credentialsPath, projectId)) {
            try {
                boolean loggedIn = userManager.login(email.trim(), password.trim());
                if (!loggedIn) {
                    logger.error("Demo login failed for email {}: invalid credentials", email);
                    return "Login failed: Invalid demo credentials";
                }

                logger.info("User logged in successfully in demo mode: {}", email);
                return "demo-token:" + email.trim();
            } catch (IOException e) {
                logger.error("Demo login failed for email {}: {}", email, e.getMessage());
                return "Login failed: " + e.getMessage();
            }
        }

        try {
            UserRecord userRecord = getFirebaseAuth().getUserByEmail(email.trim());
            String userId = userRecord.getUid();

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

    public boolean logout(String idToken) {
        if (idToken == null || idToken.trim().isEmpty()) {
            logger.error("ID token cannot be null or empty");
            return false;
        }

        if (FirebaseConfigurationSupport.isDemoMode(credentialsPath, projectId)) {
            logger.info("Demo logout completed");
            return true;
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
