package org.vaadin.numerosity.Subsystems;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class UserManager {
    private static final Path BASE_DIR = Paths.get("Database", "UserData");
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserManager(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        initializeDirectories();
    }

    private void initializeDirectories() {
        try {
            Files.createDirectories(BASE_DIR);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize user directories", e);
        }
    }

    public String signup(String username, String password, String dob) throws IOException {
        // Check if username exists
        if (isUsernameTaken(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create user directory
        String userId = UUID.randomUUID().toString();
        Path userDir = BASE_DIR.resolve(userId);
        Files.createDirectories(userDir);

        // Create user info JSON
        UserInfo userInfo = new UserInfo(
                userId,
                dob,
                username,
                passwordEncoder.encode(password));
        writeJson(userDir.resolve("user_info.json"), userInfo);

        // Create empty answers JSON
        UserAnswer initialAnswer = new UserAnswer();
        writeJson(userDir.resolve("user_answers.json"), Collections.singletonList(initialAnswer));

        return userId;
    }

    public boolean login(String username, String password) throws IOException {
        Path userDir = findUserDirectory(username);
        if (userDir == null)
            return false;

        UserInfo userInfo = readJson(userDir.resolve("user_info.json"), UserInfo.class);
        return passwordEncoder.matches(password, userInfo.password);
    }

    public void deleteAccount(String userId) throws IOException {
        Path userDir = BASE_DIR.resolve(userId);
        if (Files.exists(userDir)) {
            Files.walk(userDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(this::deleteSilently);
        }
    }

    // Getter methods
    public String getUserId(String username) throws IOException {
        return getUserInfo(username).id;
    }

    public String getUserDob(String username) throws IOException {
        return getUserInfo(username).dob;
    }

    public String getUsername(String userId) throws IOException {
        return readJson(BASE_DIR.resolve(userId).resolve("user_info.json"), UserInfo.class).username;
    }

    private UserInfo getUserInfo(String username) throws IOException {
        Path userDir = findUserDirectory(username);
        return readJson(userDir.resolve("user_info.json"), UserInfo.class);
    }

    private boolean isUsernameTaken(String username) throws IOException {
        return findUserDirectory(username) != null;
    }

    private Path findUserDirectory(String username) throws IOException {
        if (!Files.exists(BASE_DIR))
            return null;

        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(BASE_DIR)) {
            for (Path dir : dirs) {
                UserInfo info = readJson(dir.resolve("user_info.json"), UserInfo.class);
                if (info != null && info.username.equals(username)) {
                    return dir;
                }
            }
        }
        return null;
    }

    private <T> T readJson(Path path, Class<T> type) throws IOException {
        return objectMapper.readValue(path.toFile(), type);
    }

    private void writeJson(Path path, Object value) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), value);
    }

    private void deleteSilently(Path path) {
        try {
            Files.delete(path);
        } catch (IOException ignored) {
        }
    }

    // Data classes for JSON serialization
    private static class UserInfo {
        public String id;
        public String dob;
        public String username;
        public String password;

        public UserInfo() {
        } // For Jackson

        public UserInfo(String id, String dob, String username, String password) {
            this.id = id;
            this.dob = dob;
            this.username = username;
            this.password = password;
        }
    }

    private static class UserAnswer {
        public String id = "q1";
        public Boolean is_correct = null;
        public String user_answer = null;
        public Integer user_time_taken = null;
    }

    private static final String FILE_PATH = "user_answers.json";
    private Gson gson = new Gson();

    // Method to save user answers to a JSON file
    public void saveUserAnswers(Map<String, Object> userAnswers) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            gson.toJson(userAnswers, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
