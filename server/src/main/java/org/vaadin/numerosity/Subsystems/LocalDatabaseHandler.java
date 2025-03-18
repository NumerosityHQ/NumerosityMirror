// LocalDatabaseHandler.java
package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class LocalDatabaseHandler {

    private final Gson gson = new Gson();
    // private final QuestionLoader questionLoader;
    // String directory = "Database/Bank/AlgebraOne/Easy";
    // String questionId = "q1"; //No need for this here

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

    private String directory = AlgebraOneEasy; // temporary solution to choose directory. Make this configurable later

    private final String[] directories = { AlgebraOneEasy, AlgebraOneMedium, AlgebraOneHard, AlgebraTwoEasy,
            AlgebraTwoMedium,
            AlgebraTwoHard, GeometryEasy, GeometryMedium, GeometryHard, PrecalculusEasy, PrecalculusMedium,
            PrecalculusHard, CalculusEasy, CalculusMedium, CalculusHard, DailyEasy, DailyMedium, DailyHard,
            DailyChallenge };

    private final String[] questions = { "q1", "q2", "q3" }; // Ideally read these from the directory

    // No longer needs LocalDatabaseHandler
    // @Autowired //Not needed now
    // public QuestionLoader(LocalDatabaseHandler localDb) {
    // this.localDb = localDb;
    // }

    // These loadAsText and loadAsLatex were moved to a more appropriate class

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
        String directory = getRandomDirectory();
        String questionId = getRandomQuestion();
        Path path = Paths.get(directory, questionId + ".json");

        if (!Files.exists(path)) {
            throw new IOException("Question file not found: " + path);
        }

        String content = new String(Files.readAllBytes(path));
        return gson.fromJson(content, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    // the directory refers to the level of the question as defined above
    public Map<String, Object> loadSpecificQuestion(String questionId, String directory) throws Exception {
        Path path = Paths.get(directory, questionId + ".json");

        if (!Files.exists(path)) {
            throw new IOException("Question file not found: " + path);
        }

        String content = new String(Files.readAllBytes(path));
        return gson.fromJson(content, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    // load random question with specific directory or level
    // question id should be random but the directory should be specified
    public Map<String, Object> loadRandomQuestionByLevel(String directory) throws Exception {
        String questionId = getRandomQuestion();
        return loadSpecificQuestion(questionId, directory);
    }

    // set directory form user input
    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
