// LocalDatabaseHandler.java
package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class LocalDatabaseHandler {

    private final Gson gson = new Gson();
    private final QuestionLoader questionLoader;

    @Autowired
    public LocalDatabaseHandler(QuestionLoader questionLoader) {
        this.questionLoader = questionLoader;
    }

    public void saveQuestion(String questionId, Map<String, Object> questionData) throws Exception {
        String directory = questionLoader.getDirectory(); // Use the getter
        Path dirPath = Paths.get(directory);

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path path = dirPath.resolve(questionId + ".json");
        Files.write(path, gson.toJson(questionData).getBytes());
    }

    public Map<String, Object> loadRandomQuestion() throws Exception {
        String directory = questionLoader.getRandomDirectory();
        String questionId = questionLoader.getRandomQuestion();
        Path path = Paths.get(directory, questionId + ".json");

        if (!Files.exists(path)) {
            throw new IOException("Question file not found: " + path);
        }

        String content = new String(Files.readAllBytes(path));
        return gson.fromJson(content, new TypeToken<Map<String, Object>>() {}.getType());
    }
}
