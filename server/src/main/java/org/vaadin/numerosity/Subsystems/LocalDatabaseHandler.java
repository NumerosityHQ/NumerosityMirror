package org.vaadin.numerosity.Subsystems;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.vaadin.numerosity.Application;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class LocalDatabaseHandler {

    private final Application application;

    private final Gson gson = new Gson();
    private String chosenQuestion;

    // Map<String, Object> question = localDbHandler.loadRandomQuestion();

    private Map<String, Object> chosenQuestionMap;

    private final String AlgebraOneEasy = "Database/Bank/AlgebraOne/Easy";
    private final String AlgebraOneMedium = "Database/Bank/AlgebraOne/Medium";
    private final String AlgebraOneHard = "Database/Bank/AlgebraOne/Hard";
    private final String AlgebraTwoEasy = "Database/Bank/AlgebraTwo/Easy";
    private final String AlgebraTwoMedium = "Database/Bank/AlgebraTwo/Medium";
    private final String AlgebraTwoHard = "Database/Bank/AlgebraTwo/Hard";
    private final String GeometryEasy = "Database/Bank/Geometry/Easy";
    private final String GeometryMedium = "Database/Bank/Geometry/Medium";
    private final String GeometryHard = "Database/Bank/Geometry/Hard";
    private final String PrecalculusEasy = "Database/Bank/Precalculus/Easy";
    private final String PrecalculusMedium = "Database/Bank/Precalculus/Medium";
    private final String PrecalculusHard = "Database/Bank/Precalculus/Hard";
    private final String CalculusEasy = "Database/Bank/Calculus/Easy";
    private final String CalculusMedium = "Database/Bank/Calculus/Medium";
    private final String CalculusHard = "Database/Bank/Calculus/Hard";
    private final String DailyEasy = "Database/Bank/Daily/Easy";
    private final String DailyMedium = "Database/Bank/Daily/Medium";
    private final String DailyHard = "Database/Bank/Daily/Hard";
    private final String DailyChallenge = "Database/Bank/Daily/Challenge";
    private final String testing_directory = "Database/Bank/";

    private String directory = AlgebraOneEasy; // temporary solution to choose directory. Make this configurable later

    private final String[] directories = { AlgebraOneEasy, AlgebraOneMedium, AlgebraOneHard, AlgebraTwoEasy,
            AlgebraTwoMedium,
            AlgebraTwoHard, GeometryEasy, GeometryMedium, GeometryHard, PrecalculusEasy, PrecalculusMedium,
            PrecalculusHard, CalculusEasy, CalculusMedium, CalculusHard, DailyEasy, DailyMedium, DailyHard,
            DailyChallenge };

    private final String[] questions = { "q1", "q2", "q3" };
    Path databasePath = Paths.get(testing_directory, "questions.json");

    LocalDatabaseHandler(Application application) {
        this.application = application;
    } // Ideally read these from the directory

    // load random question from the database with the known paths
    public String getRandomDirectory() {
        Random random = new Random();
        int randomIndex = random.nextInt(directories.length);
        return directories[randomIndex];
    }

    // load random question
    public String getRandomQuestion() {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.length);
        return questions[randomIndex];
    }

    public String getDirectory() {
        return directory;
    }

    public void saveQuestion(String questionId, Map<String, Object> questionData) throws Exception {
        String directory = getDirectory(); // Use the getter
        Path dirPath = Paths.get(directory);

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path path = dirPath.resolve(questionId + ".json");
        Files.write(path, gson.toJson(questionData).getBytes());
    }

    public Map<String, Object> loadRandomQuestion() throws Exception {
        chosenQuestion = getRandomQuestion();
        return loadSpecificQuestion(chosenQuestion, testing_directory);
    }

    public Map<String, Object> getChosenQuestionMap() throws Exception {
        return loadSpecificQuestion(chosenQuestion, testing_directory);
    }

    public String getChosenQuestion() throws Exception {
        return chosenQuestion;
    }

    /*
     * the directory refers to the level of the question as defined above
     */
    public Map<String, Object> loadSpecificQuestion(String questionId, String filePath) throws Exception {
        Path path = Paths.get(testing_directory, "questions.json");
        String content = new String(Files.readAllBytes(path));

        JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
        JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

        for (JsonElement questionElement : questionsArray) {
            JsonObject questionObject = questionElement.getAsJsonObject();
            if (questionObject.get("id").getAsString().equals(questionId)) {
                // Convert the found question to a Map<String, Object>
                return convertJsonObjectToMap(questionObject);
            }
        }

        // If the question is not found, throw an exception
        throw new Exception("Question with id " + questionId + " not found in the file.");
    }

    // Helper method to convert JsonObject to Map<String, Object>
    private Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), gson.fromJson(entry.getValue(), Object.class));
        }
        return map;
    }

    // load random question with specific directory or level
    // question id should be random but the directory should be specified
    public Map<String, Object> loadRandomQuestionByLevel(String directory) throws Exception {
        String questionId = getRandomQuestion();
        return loadSpecificQuestion(questionId, directory);
    }

    /**
     * Retrieves the text of a specific answer option for a given question ID.
     *
     * @param questionId   The ID of the question.
     * @param optionNumber The number of the option (1 for the first option, 2 for
     *                     the second, etc.).
     * @return The text of the specified answer option, or null if not found.
     * @throws Exception If there is an error reading the file or parsing the JSON.
     */
    public String getAnswerChoiceText(String questionId, String optionId) throws Exception {
        Path path = Paths.get(testing_directory, "questions.json");
        String content = new String(Files.readAllBytes(path));

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

        // If the question is not found, throw an exception
        throw new Exception("Question with id " + questionId + " not found in the file.");
    }

    // set directory form user input
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getSpecificDirectory() {
        return testing_directory;
    }

    public void markQuestionAsAttempted(String questionId) {
        try {
            // Load the JSON file
            Path path = Paths.get(testing_directory, "questions.json");
            String content = new String(Files.readAllBytes(path));

            // Parse the JSON content
            JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
            JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

            // Iterate through the questions to find the one with the matching ID
            for (JsonElement questionElement : questionsArray) {
                JsonObject questionObject = questionElement.getAsJsonObject();
                if (questionObject.get("id").getAsString().equals(questionId)) {
                    // Mark the question as attempted
                    questionObject.addProperty("attempted", true);

                    // Save the updated JSON back to the file
                    Files.write(path, gson.toJson(rootJsonObject).getBytes());
                    System.out.println("Question with id " + questionId + " marked as attempted.");
                    return;
                }
            }

            // If the question is not found, print a message or throw an exception
            System.out.println("Question with id " + questionId + " not found in the file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markQuestionAsUnattempted(String questionId) {
        try {
            // Load the JSON file
            Path path = Paths.get(testing_directory, "questions.json");
            String content = new String(Files.readAllBytes(path));

            // Parse the JSON content
            JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
            JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

            // Iterate through the questions to find the one with the matching ID
            for (JsonElement questionElement : questionsArray) {
                JsonObject questionObject = questionElement.getAsJsonObject();
                if (questionObject.get("id").getAsString().equals(questionId)) {
                    // Mark the question as unattempted
                    questionObject.addProperty("attempted", false);

                    // Save the updated JSON back to the file
                    Files.write(path, gson.toJson(rootJsonObject).getBytes());
                    System.out.println("Question with id " + questionId + " marked as unattempted.");
                    return;
                }
            }

            // If the question is not found, print a message or throw an exception
            System.out.println("Question with id " + questionId + " not found in the file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markQuestionAsCorrect(String questionId) {
        try {
            // Load the JSON file
            Path path = Paths.get(testing_directory, "questions.json");
            String content = new String(Files.readAllBytes(path));

            // Parse the JSON content
            JsonObject rootJsonObject = gson.fromJson(content, JsonObject.class);
            JsonArray questionsArray = rootJsonObject.getAsJsonArray("questions");

            // Iterate through the questions to find the one with the matching ID
            for (JsonElement questionElement : questionsArray) {
                JsonObject questionObject = questionElement.getAsJsonObject();
                if (questionObject.get("id").getAsString().equals(questionId)) {
                    // Mark the question as correct
                    questionObject.addProperty("correct", true);

                    // Save the updated JSON back to the file
                    Files.write(path, gson.toJson(rootJsonObject).getBytes());
                    System.out.println("Question with id " + questionId + " marked as correct.");
                    return;
                }
            }

            // If the question is not found, print a message or throw an exception
            System.out.println("Question with id " + questionId + " not found in the file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
