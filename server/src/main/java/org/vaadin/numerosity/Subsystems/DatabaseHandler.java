package org.vaadin.numerosity.Subsystems;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

@Service
public class DatabaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
    private final Firestore firestore;
    private String userId; 
    private String username;

    public DatabaseHandler(Firestore firestore) {
        this.firestore = firestore;
    }

    public void createUserDocument(String userId, String username) throws ExecutionException, InterruptedException {
        if (userId == null || userId.trim().isEmpty()) {
            logger.error("User ID cannot be null or empty.");
            throw new IllegalArgumentException("User ID cannot be null or empty.");  // Or handle differently
        }
        if (username == null || username.trim().isEmpty()) {
            logger.error("Username cannot be null or empty.");
            throw new IllegalArgumentException("Username cannot be null or empty.");  // Or handle differently
        }
        this.userId = userId;
        this.username = username;

        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> userData = new HashMap<>();  // Specify type parameters
        userData.put("username", username);
        userData.put("created_at", new Date());
        userData.put("correct", 0);
        userData.put("wrong", 0);
        userData.put("answered", 0);
        userData.put("unattempted", 0);

        docRef.set(userData).get();
    }

    public void updateStatistic(String userId, String field, int delta)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> updates = new HashMap<>();  // Specify type parameters
        updates.put(field, FieldValue.increment(delta));
        docRef.update(updates).get();
    }

    public void incrementCorrect() {
        try {
            updateStatistic(userId, "correct", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing correct count:", e);
        }
    }

    public void incrementWrong() {
        try {
            updateStatistic(userId, "wrong", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing wrong count:", e);
        }
    }

    public boolean userExists(String userId) {
        DocumentReference docRef = firestore.collection("users").document(userId);
        try {
            return docRef.get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error checking user existence", e);
        }
    }

    public void saveQuestionData(String questionId, String userId, String answerId, boolean isCorrect) {

    }

    public void deleteQuestionData(String questionId, String userId) {

    }
    
    
}
