package org.vaadin.numerosity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vaadin.numerosity.service.UserService;

@RestController
@Conditional(org.vaadin.numerosity.config.FirestoreAvailableCondition.class)
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null || userDTO.getUserId() == null || userDTO.getUsername() == null) {
            return ResponseEntity.badRequest().body("User ID and username are required");
        }
        
        try {
            userService.createUser(userDTO.getUserId(), userDTO.getUsername());
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    // Additional endpoints for user operations can be added here
}
