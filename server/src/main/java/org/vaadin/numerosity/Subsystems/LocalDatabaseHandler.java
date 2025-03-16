package org.vaadin.numerosity.Subsystems;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service // Add this annotation to make it a Spring-managed bean
public class LocalDatabaseHandler {
    private final Gson gson = new Gson();

    public void saveQuestion(String directory, String questionId, Map<String, Object> questionData) throws IOException {
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath); // Create directory if missing
        }
        Path path = dirPath.resolve(questionId + ".json");
        Files.write(path, gson.toJson(questionData).getBytes());
    }
    
    public Map<String, Object> loadQuestion(String directory, String questionId) throws IOException {
        Path path = Paths.get(directory, questionId + ".json");
        if (!Files.exists(path)) {
            throw new IOException("Question file not found: " + path);
        }
        String content = new String(Files.readAllBytes(path));
        return gson.fromJson(content, new TypeToken<Map<String, Object>>(){}.getType());
    }
}

