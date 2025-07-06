package org.vaadin.numerosity.Subsystems;
// // ResponseHandler.java  (Adjust constructor)
// package org.vaadin.numerosity.Subsystems;

// import java.time.Duration;
// import java.time.Instant;
// import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// public class ResponseHandler {

//     private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
//     private final LocalDatabaseHandler localDbHandler;
//     private final DatabaseHandler dbHandler;
//     private final DataPlotter dataPlotter;
//     private String userId;
//     private String questionId;

//     public ResponseHandler(LocalDatabaseHandler localDbHandler, DatabaseHandler dbHandler, DataPlotter dataPlotter) {
//         this.localDbHandler = localDbHandler;
//         this.dbHandler = dbHandler;
//         this.dataPlotter = dataPlotter;
//     }

//     public ResponseResult handleResponse(String questionId, String userAnswer) {
//         Instant startTime = Instant.now();
//         boolean isCorrect = validateAnswer(questionId, userAnswer);
//         Instant endTime = Instant.now();
//         long timeTakenMillis = Duration.between(startTime, endTime).toMillis();

//         // Update statistics in Firestore
//         if (isCorrect) {
//             dbHandler.incrementCorrect();
//         } else {
//             dbHandler.incrementWrong();
//         }

//         return new ResponseResult(isCorrect, timeTakenMillis);
//     }

//     private boolean validateAnswer(String questionId, String userAnswer) {
//         try {
//             Map<String, Object> question = localDbHandler.loadRandomQuestion();  //Specify type
//             if (question != null && question.containsKey("answer")) {
//                 String correctAnswer = question.get("answer").toString();
//                 return correctAnswer.trim().equalsIgnoreCase(userAnswer.trim()); // Trim to avoid whitespace issues
//             } else {
//                 logger.warn("Question {} does not have a valid answer field.", questionId);
//                 return false; // Or throw an exception, depending on desired behavior
//             }
//         } catch (Exception e) {
//             logger.error("Error validating answer for question {}: {}", questionId, e.getMessage(), e);
//             return false; // Or throw an exception
//         }
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
//     }

//     public void setQuestionId(String questionId) {
//         this.questionId = questionId;
//     }

//     //Inner class for encapsulation
//     public static class ResponseResult {
//         private final boolean isCorrect;
//         private final long timeTakenMillis;

//         public ResponseResult(boolean isCorrect, long timeTakenMillis) {
//             this.isCorrect = isCorrect;
//             this.timeTakenMillis = timeTakenMillis;
//         }

//         public boolean isCorrect() {
//             return isCorrect;
//         }

//         public long getTimeTakenMillis() {
//             return timeTakenMillis;
//         }
//     }
// }
