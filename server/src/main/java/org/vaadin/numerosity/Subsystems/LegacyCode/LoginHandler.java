package org.vaadin.numerosity.Subsystems.LegacyCode;
// package org.vaadin.numerosity.Subsystems;

// import java.util.concurrent.ExecutionException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseAuthException;
// import com.google.firebase.auth.FirebaseToken;
// import com.google.firebase.auth.UserRecord;

// @Service
// public class LoginHandler {

//     @Autowired
//     private DatabaseHandler databaseHandler;

//     public String login(String email, String password) throws ExecutionException, InterruptedException {
//         try {
//             UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
//             String userId = userRecord.getUid();

//             // Ensure user exists in Firestore
//             if (!databaseHandler.userExists(userId)) {
//                 databaseHandler.createUserDocument(userId, email);
//             }

//             return FirebaseAuth.getInstance().createCustomToken(userId);
//         } catch (FirebaseAuthException e) {
//             return "Login failed: " + e.getMessage();
//         }
//     }

//     public boolean logout(String idToken) {
//         try {
//             FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//             FirebaseAuth.getInstance().revokeRefreshTokens(decodedToken.getUid());
//             return true;
//         } catch (FirebaseAuthException e) {
//             return false;
//         }
//     }
// }
