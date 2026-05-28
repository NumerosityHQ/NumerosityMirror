package org.vaadin.numerosity.repository;

import java.util.Optional;
import java.util.Map;

/**
 * Repository interface for user data operations.
 */
public interface UserRepository {

    void createUserDocument(String userId, String username);

    void incrementCorrect(String userId);

    void incrementWrong(String userId);

    Optional<Map<String, Object>> getUserStats(String userId);

    boolean userExists(String userId);
}
