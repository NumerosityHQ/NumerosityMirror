package org.vaadin.numerosity.Subsystems;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Database seeder for loading math questions into the system.
 * Loads questions from JSON files and makes them available for the quiz application.
 */
@Component
public class QuestionSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(QuestionSeeder.class);

    @Autowired
    private QuestionContentLoader questionContentLoader;

    @Override
    public void run(String... args) throws Exception {
        seedQuestions();
    }

    /**
     * Seeds the database with questions from the comprehensive JSON file.
     */
    public void seedQuestions() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("data/questions-comprehensive.json");
            
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, List<Map<String, Object>>> data = mapper.readValue(
                    inputStream, 
                    new com.fasterxml.jackson.core.type.TypeReference<>() {}
                );
                
                List<Map<String, Object>> questions = data.get("questions");
                logger.info("Loaded {} questions from comprehensive database", questions.size());
                
                // Log question distribution by category
                questions.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                        q -> (String) q.get("category"),
                        java.util.stream.Collectors.counting()
                    ))
                    .forEach((category, count) -> 
                        logger.info("Category '{}': {} questions", category, count)
                    );
                
            }
        } catch (IOException e) {
            logger.error("Failed to load questions: {}", e.getMessage());
        }
    }

    /**
     * Gets questions by category.
     *
     * @param category the category to filter by
     * @return list of questions in the category
     */
    public List<Map<String, Object>> getQuestionsByCategory(String category) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("data/questions-comprehensive.json");
            
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, List<Map<String, Object>>> data = mapper.readValue(
                    inputStream,
                    new com.fasterxml.jackson.core.type.TypeReference<>() {}
                );
                
                return data.get("questions").stream()
                    .filter(q -> category.equals(q.get("category")))
                    .toList();
            }
        } catch (IOException e) {
            logger.error("Failed to get questions by category: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Gets questions by difficulty.
     *
     * @param difficulty the difficulty level to filter by
     * @return list of questions at the specified difficulty
     */
    public List<Map<String, Object>> getQuestionsByDifficulty(String difficulty) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("data/questions-comprehensive.json");
            
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, List<Map<String, Object>>> data = mapper.readValue(
                    inputStream,
                    new com.fasterxml.jackson.core.type.TypeReference<>() {}
                );
                
                return data.get("questions").stream()
                    .filter(q -> difficulty.equals(q.get("difficulty")))
                    .toList();
            }
        } catch (IOException e) {
            logger.error("Failed to get questions by difficulty: {}", e.getMessage());
            return List.of();
        }
    }
}