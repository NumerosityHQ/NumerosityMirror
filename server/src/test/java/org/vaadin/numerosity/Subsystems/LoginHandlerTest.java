package org.vaadin.numerosity.Subsystems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class LoginHandlerTest {

    @Mock
    private DatabaseHandler databaseHandler;

    @Mock
    private UserManager userManager;

    @InjectMocks
    private LoginHandler loginHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(loginHandler, "credentialsPath", "");
        ReflectionTestUtils.setField(loginHandler, "projectId", "");
    }

    @Test
    void testSignupEmailNull() {
        String result = assertDoesNotThrow(() -> loginHandler.signup(null, "password123"));

        assertEquals("Signup failed: Email cannot be null or empty", result);
    }

    @Test
    void testSignupEmailEmpty() {
        String result = assertDoesNotThrow(() -> loginHandler.signup("", "password123"));

        assertEquals("Signup failed: Email cannot be null or empty", result);
    }

    @Test
    void testSignupPasswordNull() {
        String result = assertDoesNotThrow(() -> loginHandler.signup("test@example.com", null));

        assertEquals("Signup failed: Password cannot be null or empty", result);
    }

    @Test
    void testSignupPasswordEmpty() {
        String result = assertDoesNotThrow(() -> loginHandler.signup("test@example.com", ""));

        assertEquals("Signup failed: Password cannot be null or empty", result);
    }

    @Test
    void testLoginEmailNull() {
        String result = assertDoesNotThrow(() -> loginHandler.login(null, "password123"));

        assertEquals("Login failed: Email cannot be null or empty", result);
    }

    @Test
    void testLoginEmailEmpty() {
        String result = assertDoesNotThrow(() -> loginHandler.login("", "password123"));

        assertEquals("Login failed: Email cannot be null or empty", result);
    }

    @Test
    void testLoginPasswordNull() {
        String result = assertDoesNotThrow(() -> loginHandler.login("test@example.com", null));

        assertEquals("Login failed: Password cannot be null or empty", result);
    }

    @Test
    void testLoginPasswordEmpty() {
        String result = assertDoesNotThrow(() -> loginHandler.login("test@example.com", ""));

        assertEquals("Login failed: Password cannot be null or empty", result);
    }

    @Test
    void testLogoutNullToken() {
        boolean result = loginHandler.logout(null);

        assertFalse(result);
    }

    @Test
    void testLogoutEmptyToken() {
        boolean result = loginHandler.logout("");

        assertFalse(result);
    }

    @Test
    void testDemoSignupUsesLocalUserManager() throws Exception {
        when(userManager.signup("test@example.com", "password123", "demo")).thenReturn("local-user-id");

        String result = assertDoesNotThrow(() -> loginHandler.signup("test@example.com", "password123"));

        assertEquals("Signup successful! User ID: local-user-id", result);
        verify(userManager).signup("test@example.com", "password123", "demo");
    }

    @Test
    void testDemoLoginUsesLocalUserManager() throws Exception {
        when(userManager.login("test@example.com", "password123")).thenReturn(true);

        String result = assertDoesNotThrow(() -> loginHandler.login("test@example.com", "password123"));

        assertEquals("demo-token:test@example.com", result);
        verify(userManager).login("test@example.com", "password123");
    }

    @Test
    void testDemoLogoutSkipsFirebase() {
        assertTrue(loginHandler.logout("demo-token:test@example.com"));
        verifyNoInteractions(userManager);
    }
}
