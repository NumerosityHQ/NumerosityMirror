package org.vaadin.numerosity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.numerosity.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String userId, String username) {
        userRepository.createUserDocument(userId, username);
    }

    // Additional methods for user operations
    // public String loginUser(String email, String password) {
    //     return loginHandler.login(email, password);
    // }

    // public boolean logoutUser(String token) {
    //     return loginHandler.logout(token);
    // }

}
