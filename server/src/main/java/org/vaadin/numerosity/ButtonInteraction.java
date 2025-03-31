package org.vaadin.numerosity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.repository.UserRepository;

@Service
public class ButtonInteraction implements Serializable {

    @Autowired
    private UserRepository userRepository;
    
}
