package org.vaadin.numerosity.Subsystems;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Conditional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

/**
 * Service class for handling database operations with Firestore.
 * Manages user documents, statistics, question data, and user information storage and retrieval.
 */
@Service
@Conditional(org.vaadin.numerosity.config.FirestoreAvailableCondition.class)
public class DatabaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);
    private final Firestore firestore;
    private String userId;
    private String username;

    /**
     * Constructor that injects the Firestore instance.
     *
     * @param firestore the Firestore client instance
     */
    public DatabaseHandler(Firestore firestore) {
        this.firestore = firestore;
        this.dbFirestore = firestore;
    }

    /**
     * Creates a new user document in Firestore with initial statistics.
     *
     * @param userId the unique user identifier
     * @param username the user's username
     * @throws ExecutionException if the operation fails
     * @throws InterruptedException if the operation is interrupted
     * @throws IllegalArgumentException if userId or username is null/empty
     */
    public void createUserDocument(String userId, String username) throws ExecutionException, InterruptedException {
        if (userId == null || userId.trim().isEmpty()) {
            logger.error("User ID cannot be null or empty.");
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (username == null || username.trim().isEmpty()) {
            logger.error("Username cannot be null or empty.");
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.userId = userId;
        this.username = username;

        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("created_at", new Date());
        userData.put("correct", 0);
        userData.put("wrong", 0);
        userData.put("answered", 0);
        userData.put("unattempted", 0);

        docRef.set(userData).get();
    }

    /**
     * Updates a specific statistic field for a user in Firestore by incrementing it by a delta value.
     *
     * @param userId the unique user identifier
     * @param field the name of the statistic field to update (e.g., "correct", "wrong")
     * @param delta the amount to increment the field by
     * @throws ExecutionException if the operation fails
     * @throws InterruptedException if the operation is interrupted
     */
    public void updateStatistic(String userId, String field, int delta) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put(field, FieldValue.increment(delta));
        docRef.update(updates).get();
    }

    /**
     * Increments the correct answer count for a specified user.
     *
     * @param userId the unique user identifier
     */
    public void incrementCorrect(String userId) {
        try {
            updateStatistic(userId, "correct", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing correct count:", e);
        }
    }

    /**
     * Increments the wrong answer count for a specified user.
     *
     * @param userId the unique user identifier
     */
    public void incrementWrong(String userId) {
        try {
            updateStatistic(userId, "wrong", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing wrong count:", e);
        }
    }


    /**
     * Checks if a user document exists in Firestore.
     *
     * @param userId the unique user identifier
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            logger.error("User ID cannot be null or empty in userExists check");
            return false;
        }
        DocumentReference docRef = firestore.collection("users").document(userId);
        try {
            return docRef.get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error checking user existence for userId {}: {}", userId, e.getMessage());
            return false;
        }
    }

    /**
     * Saves question data for a user, including the question ID, answer ID, and correctness.
     *
     * @param questionId the ID of the question
     * @param userId the unique user identifier
     * @param answerId the ID of the selected answer
     * @param isCorrect whether the answer was correct
     */
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

    /**
     * Deletes question data for a user based on the question ID.
     *
     * @param questionId the ID of the question to delete
     * @param userId the unique user identifier
     */
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

    /**
     * Increments the correct answer count for the current user.
     */
    public void incrementCorrect() {
        try {
            updateStatistic(userId, "correct", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing correct count:", e);
        }
    }

    /**
     * Increments the wrong answer count for the current user.
     */
    public void incrementWrong() {
        try {
            updateStatistic(userId, "wrong", 1);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error incrementing wrong count:", e);
        }
    }

    /** Alternative Firestore instance for additional operations. */
    private final Firestore dbFirestore;

    /**
     * Saves user information to Firestore.
     *
     * @param userId the unique user identifier
     * @param userInfo a map containing user information
     * @return the update time as a string, or null if failed
     */
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

    /**
     * Saves answered problem data for a user to Firestore.
     *
     * @param userId the unique user identifier
     * @param problemId the ID of the problem
     * @param problemData a map containing problem data
     * @return the update time as a string, or null if failed
     */
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

    /**
     * Retrieves user information from Firestore.
     *
     * @param userId the unique user identifier
     * @return a map containing user information, or null if not found
     */
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

    /**
     * Retrieves answered problem data for a user from Firestore.
     *
     * @param userId the unique user identifier
     * @param problemId the ID of the problem
     * @return a map containing problem data, or null if not found
     */
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

    /**
     * Creates a user answers document in Firestore.
     *
     * @param userId the unique user identifier
     * @throws ExecutionException if the operation fails
     * @throws InterruptedException if the operation is interrupted
     */
    public void createUserAnswers(String userId) throws ExecutionException, InterruptedException {
        DocumentReference answersDocRef = firestore.collection("Database/UserData").document(userId)
                .collection("UserID").document("user_answers");
        Map<String, Object> answersData = new HashMap<>();
        answersData.put("answers", new HashMap<>());

        answersDocRef.set(answersData).get();
    }

    /**
     * Creates a user info document in Firestore.
     *
     * @param userId the unique user identifier
     * @throws ExecutionException if the operation fails
     * @throws InterruptedException if the operation is interrupted
     */
    public void createUserInfo(String userId) throws ExecutionException, InterruptedException {
        DocumentReference infoDocRef = firestore.collection("Database/UserData").document(userId).collection("UserID")
                .document("user_info");
        Map<String, Object> infoData = new HashMap<>();
        infoData.put("info", new HashMap<>());

        infoDocRef.set(infoData).get();
    }

    /**
     * Sets the current user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the current username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the current username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the user ID associated with the given email from Firebase Auth.
     *
     * @param email the user's email
     * @return the user ID, or null if not found
     */
    public String getUserId(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord.getUid();
        } catch (Exception e) {
            logger.error("Error retrieving user ID from Firebase for email: {}", email, e);
            return null;
        }
    }

}
