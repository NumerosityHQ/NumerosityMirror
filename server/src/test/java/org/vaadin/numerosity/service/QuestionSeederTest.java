package org.vaadin.numerosity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vaadin.numerosity.Subsystems.QuestionSeeder;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionSeederTest {

    @Mock
    private QuestionContentLoader questionContentLoader;

    @InjectMocks
    private QuestionSeeder questionSeeder;

    @Test
    void testGetQuestionsByCategory() {
        List<Map<String, Object>> questions = questionSeeder.getQuestionsByCategory("Algebra");
        assertNotNull(questions);
    }

    @Test
    void testGetQuestionsByDifficulty() {
        List<Map<String, Object>> questions = questionSeeder.getQuestionsByDifficulty("easy");
        assertNotNull(questions);
    }

    @Test
    void testSeedQuestions() {
        assertDoesNotThrow(() -> questionSeeder.seedQuestions());
    }
}
