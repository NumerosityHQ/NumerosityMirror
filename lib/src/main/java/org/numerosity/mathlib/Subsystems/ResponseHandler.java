package org.numerosity.mathlib.Subsystems;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.numerosity.mathlib.Subsystems.DatabaseHandler;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private final LocalDatabaseHandler localDbHandler;
    private final DatabaseHandler dbHandler;
    private final DataPlotter dataPlotter;

    public ResponseHandler(LocalDatabaseHandler localDbHandler, DatabaseHandler dbHandler, DataPlotter dataPlotter) {
        this.localDbHandler = localDbHandler;
        this.dbHandler = dbHandler;
        this.dataPlotter = dataPlotter;
    }

    public ResponseResult handleResponse(String userId, String questionId, String userAnswer) {
        Instant startTime = Instant.now();
        boolean isCorrect = validateAnswer(questionId, userAnswer);
        Instant endTime = Instant.now();
        long timeTakenMillis = Duration.between(startTime, endTime).toMillis();

        // Update statistics in Firestore
        if (isCorrect) {
            dbHandler.incrementCorrect(userId);
        } else {
            dbHandler.incrementWrong(userId);
        }

        return new ResponseResult(isCorrect, timeTakenMillis);
    }

    private boolean validateAnswer(String questionId, String userAnswer) {
        try {
            Map<String, Object> question = localDbHandler.loadQuestion("questions", questionId);
            if (question != null && question.containsKey("answer")) {
                String correctAnswer = question.get("answer").toString().trim();
                return userAnswer.trim().equalsIgnoreCase(correctAnswer);
            } else {
                logger.warn("Question {} does not have a valid 'answer' field.", questionId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error loading question {} for answer validation: {}", questionId, e.getMessage(), e);
            return false;
        }
    }

    public void plotData(String userId, String questionId, boolean isCorrect, long timeTakenMillis, String difficulty,
            String questionTag) {
        dataPlotter.plotData(userId, questionId, isCorrect, timeTakenMillis, difficulty, questionTag);
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