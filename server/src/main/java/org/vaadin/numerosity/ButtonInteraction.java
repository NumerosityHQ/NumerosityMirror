package org.vaadin.numerosity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.repository.UserRepository;

@Service
public class ButtonInteraction implements Serializable {

    @Autowired
    private UserRepository userRepository;

    public void incrementCorrect(String userId) throws Exception {
        userRepository.incrementCorrect(userId);
    }

    public void incrementWrong(String userId) throws Exception {
        userRepository.incrementWrong(userId);
    }

    public String loadQuestion() throws Exception {
        // Placeholder for question loading logic (can be integrated with QuestionContentLoader)
        return "Sample Question: What is 2 + 2?";
    }

    public String getExplanation() {
        // Placeholder for explanation logic
        return "This is an explanation for the current question.";
    }
}
