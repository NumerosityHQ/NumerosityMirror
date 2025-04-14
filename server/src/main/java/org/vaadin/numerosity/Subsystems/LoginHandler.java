package org.vaadin.numerosity.Subsystems;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

@Service
public class LoginHandler {

    @Autowired
    private DatabaseHandler databaseHandler;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static UserRecord createdUser = null; // Initialize userRecord to null
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
