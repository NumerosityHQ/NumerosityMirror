package org.vaadin.numerosity.Subsystems;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.concurrent.ExecutionException;

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
            throw new IllegalArgumentException("User ID cannot be null or empty."); // Or handle differently
        }
        if (username == null || username.trim().isEmpty()) {
            logger.error("Username cannot be null or empty.");
            throw new IllegalArgumentException("Username cannot be null or empty."); // Or handle differently
        }
        this.userId = userId;
        this.username = username;

        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> userData = new HashMap<>(); // Specify type parameters
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
        Map<String, Object> updates = new HashMap<>(); // Specify type parameters
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
        DocumentReference docRef = firestore.collection("userdata").document(userId);
        Map<String, Object> questionData = new HashMap<>();
        questionData.put("questionId", questionId);
        questionData.put("answerId", answerId);
        questionData.put("isCorrect", isCorrect);
        questionData.put("timestamp", new Date());

        try {
            docRef.update("user_answers", FieldValue.arrayUnion(questionData)).get();
        } catch (Exception e) {
            logger.error("Error saving question data:", e);
        }
    }

    public void deleteQuestionData(String questionId, String userId) {
        DocumentReference docRef = firestore.collection("userdata").document(userId);
        try {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("questionId", questionId);

            docRef.update("user_answers", FieldValue.arrayRemove(questionData)).get();
        } catch (Exception e) {
            logger.error("Error deleting question data:", e);
        }
    }

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    // Method to save user info
    public String saveUserInfo(String userId, Map<String, Object> userInfo) {
        DocumentReference documentReference = dbFirestore.collection("users").document(userId);
        try {
            WriteResult writeResult = documentReference.set(userInfo).get();
            return writeResult.getUpdateTime().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save answered problem data
    public String saveAnsweredProblem(String userId, String problemId, Map<String, Object> problemData) {
        DocumentReference documentReference = dbFirestore.collection("users").document(userId)
                .collection("answeredProblems").document(problemId);
        try {
            WriteResult writeResult = documentReference.set(problemData).get();
            return writeResult.getUpdateTime().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to retrieve user info
    public Map<String, Object> getUserInfo(String userId) {
        DocumentReference documentReference = dbFirestore.collection("users").document(userId);
        try {
            DocumentSnapshot documentSnapshot = documentReference.get().get();
            if (documentSnapshot.exists()) {
                return documentSnapshot.getData();
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to retrieve answered problem data
    public Map<String, Object> getAnsweredProblem(String userId, String problemId) {
        DocumentReference documentReference = dbFirestore.collection("users").document(userId)
                .collection("answeredProblems").document(problemId);
        try {
            DocumentSnapshot documentSnapshot = documentReference.get().get();
            if (documentSnapshot.exists()) {
                return documentSnapshot.getData();
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
