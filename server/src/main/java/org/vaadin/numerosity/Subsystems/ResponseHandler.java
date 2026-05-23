package org.vaadin.numerosity.Subsystems;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user responses to questions.
 */
@Service
@Conditional(org.vaadin.numerosity.config.FirestoreAvailableCondition.class)
public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private final LocalDatabaseHandler localDbHandler;
    private final DatabaseHandler dbHandler;
    private final DataPlotter dataPlotter;
    private String userId;
    private String questionId;

    public ResponseHandler(LocalDatabaseHandler localDbHandler, DatabaseHandler dbHandler, DataPlotter dataPlotter) {
        this.localDbHandler = localDbHandler;
        this.dbHandler = dbHandler;
        this.dataPlotter = dataPlotter;
    }

    public ResponseResult handleResponse(String questionId, String userAnswer, String difficulty, String subject) {
        if (questionId == null || questionId.trim().isEmpty()) {
            logger.error("Question ID cannot be null or empty");
            return new ResponseResult(false, 0L);
        }
        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            logger.error("User answer cannot be null or empty");
            return new ResponseResult(false, 0L);
        }

        Instant startTime = Instant.now();
        boolean isCorrect = validateAnswer(questionId, userAnswer);
        Instant endTime = Instant.now();
        long timeTakenMillis = Duration.between(startTime, endTime).toMillis();

        try {
            if (isCorrect) {
                dbHandler.incrementCorrect();
            } else {
                dbHandler.incrementWrong();
            }

            dbHandler.saveQuestionData(questionId, userId, userAnswer, isCorrect);

            dataPlotter.plotData(userId, questionId, isCorrect, timeTakenMillis, difficulty, subject);

            logger.info("Response handled for user {} on question {}: correct={}, time={}ms",
                    userId, questionId, isCorrect, timeTakenMillis);
        } catch (Exception e) {
            logger.error("Error handling response for user {} on question {}: {}",
                    userId, questionId, e.getMessage(), e);
        }

        return new ResponseResult(isCorrect, timeTakenMillis);
    }

    private boolean validateAnswer(String questionId, String userAnswer) {
        try {
            Map<String, Object> question = localDbHandler.loadRandomQuestion();
            if (question != null && question.containsKey("answer")) {
                String correctAnswer = question.get("answer").toString();
                return correctAnswer.trim().equalsIgnoreCase(userAnswer.trim());
            } else {
                logger.warn("Question {} does not have a valid answer field.", questionId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error validating answer for question {}: {}", questionId, e.getMessage(), e);
            return false;
        }
    }

    public void setUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            logger.warn("Attempted to set null or empty user ID");
            return;
        }
        this.userId = userId.trim();
    }

    public void setQuestionId(String questionId) {
        if (questionId == null || questionId.trim().isEmpty()) {
            logger.warn("Attempted to set null or empty question ID");
            return;
        }
        this.questionId = questionId.trim();
    }

    public static class ResponseResult {
        private final boolean isCorrect;
        private final long timeTakenMillis;

        public ResponseResult(boolean isCorrect, long timeTakenMillis) {
            this.isCorrect = isCorrect;
            this.timeTakenMillis = timeTakenMillis;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public long getTimeTakenMillis() {
            return timeTakenMillis;
        }
    }
}
