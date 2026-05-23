package org.vaadin.numerosity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.repository.UserRepository;

/**
 * Service class for user operations.
 */
@Service
@Conditional(org.vaadin.numerosity.config.FirestoreAvailableCondition.class)
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String userId, String username) {
        userRepository.createUserDocument(userId, username);
    }
}
