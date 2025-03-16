package org.vaadin.numerosity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.repository.UserRepository;
import org.vaadin.numerosity.Subsystems.DatabaseHandler;
import org.vaadin.numerosity.Subsystems.QuestionLoader;

@Service
public class ButtonInteraction implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired DatabaseHandler dbHandler;

    @Autowired  //  <-- ADD THIS ANNOTATION
    private QuestionLoader questionLoader;

    public String greet(String name) throws Exception {
        // userRepository.createUserDocument(name, name);
        dbHandler.createUserDocument(name, name);
        return questionLoader.loadAsText();

    }
}
