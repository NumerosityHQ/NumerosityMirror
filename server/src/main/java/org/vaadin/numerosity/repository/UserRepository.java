package org.vaadin.numerosity.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vaadin.numerosity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    void createUserDocument(String userId, String username);

    @Modifying
    @Query("UPDATE User u SET u.incrementCorrect = u.incrementCorrect + 1 WHERE u.username = :username")
    void incrementCorrect(@Param("username") String username);

    void incrementWrong(String userId);

    // New methods for REST API
    Optional<Map<String, Object>> getUserStats(String userId);

    boolean userExists(String userId);

    User findByUsername(String username);

}