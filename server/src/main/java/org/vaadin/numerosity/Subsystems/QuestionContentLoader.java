package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuestionContentLoader {

    private final String questionsFile = "questions.json"; // Path to your JSON file
    private JsonNode rootNode; // Store the root JSON node

    public QuestionContentLoader() {
        try {
            // Load the JSON data when the service is created
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(questionsFile);
            if (inputStream == null) {
                throw new IOException("Could not find file: " + questionsFile);
            }
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(inputStream);
        } catch (IOException e) {
            // Handle exception (e.g., log it, throw a runtime exception)
            System.err.println("Error loading questions from JSON: " + e.getMessage());
            rootNode = null; // Ensure rootNode is null to prevent further errors
        }
    }

    public Map<String, Object> loadAsMap() throws IOException {
        if (rootNode == null) {
            throw new IOException("Questions not loaded.");
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(rootNode, Map.class);
    }

    public String loadAsText() throws IOException {
        if (rootNode == null) {
            throw new IOException("Questions not loaded.");
        }

        // Get the first question text from the JSON
        JsonNode questionsNode = rootNode.get("questions");
        if (questionsNode != null && questionsNode.isArray() && questionsNode.size() > 0) {
            JsonNode firstQuestion = questionsNode.get(0);
            JsonNode textNode = firstQuestion.get("text");
            if (textNode != null) {
                return textNode.asText();
            }
        }

        return "No questions available.";
    }

    public String getAnswerChoice(Map<String, Object> questionMap, String choiceId) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            List<Map<String, Object>> options = (List<Map<String, Object>>) firstQuestion.get("options");
            if (options != null) {
                for (Map<String, Object> option : options) {
                    if (option.get("id").equals(choiceId)) {
                        return (String) option.get("text");
                    }
                }
            }
        }
        return null; // Or a default value if needed
    }

   public String getCorrectAnswerKey(Map<String, Object> questionMap) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            return (String) firstQuestion.get("correct_option_id");
        }
        return null; // Or a default value if needed
    }

    //Get question ID
    public String getQuestionId(Map<String, Object> questionMap) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            return (String) firstQuestion.get("id");
        }
        return null; // Or a default value if needed
    }

    //Get question Text
    public String getQuestionText(Map<String, Object> questionMap) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            return (String) firstQuestion.get("text");
        }
        return null; // Or a default value if needed
    }

    //Get question difficulty
     public String getQuestionDifficulty(Map<String, Object> questionMap) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            return (String) firstQuestion.get("difficulty");
        }
        return null; // Or a default value if needed
    }

    //Get question Tags
    public List<String> getQuestionTags(Map<String, Object> questionMap) {
        List<Map<String, Object>> questions = (List<Map<String, Object>>) questionMap.get("questions");
        if (questions != null && !questions.isEmpty()) {
            Map<String, Object> firstQuestion = questions.get(0);
            return (List<String>) firstQuestion.get("tags");
        }
        return null; // Or a default value if needed
    }

    public String getAnswerChoice(String choiceId) throws IOException {
         if (rootNode == null) {
            throw new IOException("Questions not loaded.");
        }

        // Get the first question text from the JSON
        JsonNode questionsNode = rootNode.get("questions");
        if (questionsNode != null && questionsNode.isArray() && questionsNode.size() > 0) {
            JsonNode firstQuestion = questionsNode.get(0);
            JsonNode optionsNode = firstQuestion.get("options");

            if (optionsNode != null && optionsNode.isArray()) {
                for (JsonNode option : optionsNode) {
                    if (choiceId.equals(option.get("id").asText())) {
                        return option.get("text").asText();
                    }
                }
            }
        }

        return "Answer not available.";
    }

    public String getCorrectAnswerKey() throws IOException {
        if (rootNode == null) {
            throw new IOException("Questions not loaded.");
        }

        // Get the first question text from the JSON
        JsonNode questionsNode = rootNode.get("questions");
        if (questionsNode != null && questionsNode.isArray() && questionsNode.size() > 0) {
            JsonNode firstQuestion = questionsNode.get(0);
            JsonNode correctAnswerNode = firstQuestion.get("correct_option_id");
            if (correctAnswerNode != null) {
                return correctAnswerNode.asText();
            }
        }

        return "Correct answer not available.";
    }
}
