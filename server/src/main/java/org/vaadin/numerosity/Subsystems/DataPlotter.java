package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/**
 * Service class for plotting and saving data related to user question interactions.
 * Handles creation and storage of plot data in JSON format.
 */
@Service
public class DataPlotter {

    private static final Logger logger = LoggerFactory.getLogger(DataPlotter.class);
    private static final String PLOT_DIR = "database/dataplots";
    private final Gson gson = new Gson();

    /**
     * Constructor that initializes the DataPlotter by ensuring the plot directory exists.
     */
    public DataPlotter() {
        Path plotDirPath = Paths.get(PLOT_DIR);
        if (!Files.exists(plotDirPath)) {
            try {
                Files.createDirectories(plotDirPath);
                logger.info("Created data plot directory: {}", PLOT_DIR);
            } catch (IOException e) {
                logger.error("Failed to create data plot directory {}: {}", PLOT_DIR, e.getMessage(), e);
            }
        }
    }

    /**
     * Plots data for a user's question interaction and saves it to a file.
     *
     * @param userId the user ID
     * @param questionId the question ID
     * @param correct whether the answer was correct
     * @param timeTakenMillis time taken to answer in milliseconds
     * @param difficulty the difficulty level
     * @param questionTag the question tag
     */
    public void plotData(String userId, String questionId, boolean correct, long timeTakenMillis, String difficulty,
            String questionTag) {
        try {
            Map<String, Object> plotData = createPlotData(userId, questionId, correct, timeTakenMillis, difficulty,
                    questionTag);
            savePlotData(plotData);
        } catch (IOException e) {
            logger.error("Failed to plot data for user {} and question {}: {}", userId, questionId, e.getMessage(), e);
        }
    }

    /**
     * Creates a map of plot data from the given parameters.
     *
     * @param userId the user ID
     * @param questionId the question ID
     * @param correct whether the answer was correct
     * @param timeTakenMillis time taken to answer in milliseconds
     * @param difficulty the difficulty level
     * @param questionTag the question tag
     * @return the plot data map
     */
    private Map<String, Object> createPlotData(String userId, String questionId, boolean correct, long timeTakenMillis,
            String difficulty, String questionTag) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("id", questionId);
        data.put("correct", correct);
        data.put("time", timeTakenMillis);
        data.put("difficulty", difficulty);
        data.put("question_tag", questionTag);

        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"));
        String timestamp = formatter.format(now);

        data.put("answered_at", timestamp);
        data.put("updated_at", timestamp);
        return data;
    }

    /**
     * Saves the plot data to a JSON file.
     *
     * @param plotData the data to save
     * @throws IOException if saving fails
     */
    private void savePlotData(Map<String, Object> plotData) throws IOException {
        String userId = (String) plotData.get("user_id");
        String questionId = (String) plotData.get("id");
        if (userId == null || userId.isEmpty() || questionId == null || questionId.isEmpty()) {
            logger.warn("User ID or Question ID is missing. Cannot save plot data.");
            return;
        }

        String filename = String.format("%s_%s_%s.json", userId, questionId, Instant.now().toEpochMilli());
        Path filePath = Paths.get(PLOT_DIR, filename).toAbsolutePath();

        try {
            String jsonData = gson.toJson(plotData);
            Files.write(filePath, jsonData.getBytes());
            logger.info("Saved plot data to: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to save plot data to {}: {}", filePath, e.getMessage(), e);
            throw e;
        }
    }
}
