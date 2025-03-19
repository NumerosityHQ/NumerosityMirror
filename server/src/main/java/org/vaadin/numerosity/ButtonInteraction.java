package org.vaadin.numerosity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.Subsystems.DatabaseHandler;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;
import org.vaadin.numerosity.repository.UserRepository;

@Service
public class ButtonInteraction implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseHandler dbHandler;

    @Autowired
    private QuestionContentLoader questionContentLoader;

    public String greet(String name) throws Exception {
        dbHandler.createUserDocument(name, name);
        return questionContentLoader.loadAsText();
    }

    public void incrementAction() throws Exception {
        dbHandler.incrementCorrect();
    }

    public void decrementAction() throws Exception {
        dbHandler.incrementWrong();
    }
}
