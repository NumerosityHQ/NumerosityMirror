package org.vaadin.numerosity.repository.exception;

/**
 * Exception for database operations.
 */
public class DbException extends RuntimeException {

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
