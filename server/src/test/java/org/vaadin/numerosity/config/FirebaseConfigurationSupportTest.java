package org.vaadin.numerosity.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FirebaseConfigurationSupportTest {

    @Test
    void placeholderFirebaseJsonFallsBackToDemoMode() {
        String placeholderJson = """
                {
                  "type": "service_account",
                  "project_id": "YOUR_PROJECT_ID",
                  "private_key_id": "YOUR_PRIVATE_KEY_ID",
                  "private_key": "YOUR_PRIVATE_KEY",
                  "client_email": "YOUR_CLIENT_EMAIL"
                }
                """;

        assertFalse(FirebaseConfigurationSupport.hasRealFirebaseConfigurationFromJson(placeholderJson, "numerosity-583f5"));
        assertTrue(FirebaseConfigurationSupport.isDemoModeFromJson(placeholderJson, "numerosity-583f5"));
    }

    @Test
    void populatedFirebaseJsonKeepsFirebaseMode() {
        String configuredJson = """
                {
                  "type": "service_account",
                  "project_id": "numerosity-583f5",
                  "private_key_id": "1234567890",
                  "private_key": "-----BEGIN PRIVATE KEY-----\\nabc\\n-----END PRIVATE KEY-----\\n",
                  "client_email": "firebase-adminsdk@example.iam.gserviceaccount.com"
                }
        """;

        assertTrue(FirebaseConfigurationSupport.hasRealFirebaseConfigurationFromJson(configuredJson, "numerosity-583f5"));
        assertFalse(FirebaseConfigurationSupport.isDemoModeFromJson(configuredJson, "numerosity-583f5"));
    }
}
