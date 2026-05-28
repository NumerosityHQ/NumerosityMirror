package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class LocalDatabaseHandler {

    private final Gson gson = new Gson();
    private String chosenQuestion;
    private Map<String, Object> chosenQuestionMap;

    private final List<String> allQuestionIds = new ArrayList<>();
    private final String questionsResourcePath = "data/questions-comprehensive.json";

    private String directory = "all";

    public LocalDatabaseHandler() {
        loadQuestionIds();
    }

    private void loadQuestionIds() {
        try {
            ClassPathResource resource = new ClassPathResource(questionsResourcePath);
            try (InputStream inputStream = resource.getInputStream()) {
                String content = new String(inputStream.readAllBytes());
                JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
                JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");
                for (JsonElement questionElement : questionsArray) {
                    JsonObject questionObject = questionElement.getAsJsonObject();
                    allQuestionIds.add(questionObject.get("id").getAsString());
                }
            }
        } catch (IOException e) {
            for (int i = 1; i <= 20; i++) {
                allQuestionIds.add("q" + i);
            }
        }
    }

    public String getRandomQuestion() {
        Random random = new Random();
        int randomIndex = random.nextInt(allQuestionIds.size());
        return allQuestionIds.get(randomIndex);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Map<String, Object> loadRandomQuestion() throws Exception {
        chosenQuestion = getRandomQuestion();
        return loadSpecificQuestion(chosenQuestion);
    }

    public Map<String, Object> getChosenQuestionMap() throws Exception {
        return loadSpecificQuestion(chosenQuestion);
    }

    public String getChosenQuestion() throws Exception {
        return chosenQuestion;
    }

    public Map<String, Object> loadSpecificQuestion(String questionId) throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource(questionsResourcePath);
            try (InputStream inputStream = resource.getInputStream()) {
                String content = new String(inputStream.readAllBytes());
                JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
                JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

                for (JsonElement questionElement : questionsArray) {
                    JsonObject questionObject = questionElement.getAsJsonObject();
                    if (questionObject.get("id").getAsString().equals(questionId)) {
                        return convertJsonObjectToMap(questionObject);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Failed to load questions: " + e.getMessage());
        }

        throw new Exception("Question with id " + questionId + " not found in the file.");
    }

    private Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), gson.fromJson(entry.getValue(), Object.class));
        }
        return map;
    }

    /**
     * Retrieves the text of a specific answer option for a given question ID.
     *
     * @param questionId   The ID of the question.
     * @param optionId     The option ID (a, b, c, d).
     * @return The text of the specified answer option, or null if not found.
     * @throws Exception If there is an error reading the file or parsing the JSON.
     */
    public String getAnswerChoiceText(String questionId, String optionId) throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource(questionsResourcePath);
            try (InputStream inputStream = resource.getInputStream()) {
                String content = new String(inputStream.readAllBytes());
                JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
                JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

                for (JsonElement questionElement : questionsArray) {
                    JsonObject questionObject = questionElement.getAsJsonObject();
                    if (questionObject.get("id").getAsString().equals(questionId)) {
                        JsonArray optionsArray = questionObject.getAsJsonArray("options");
                        for (JsonElement optionElement : optionsArray) {
                            JsonObject optionObject = optionElement.getAsJsonObject();
                            if (optionObject.get("id").getAsString().equals(optionId)) {
                                return optionObject.get("text").getAsString();
                            }
                        }
                        throw new IllegalArgumentException("Invalid option id: " + optionId + " for question " + questionId);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Failed to load questions: " + e.getMessage());
        }

        throw new Exception("Question with id " + questionId + " not found in the file.");
    }
}
