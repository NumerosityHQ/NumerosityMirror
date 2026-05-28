package org.vaadin.numerosity.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionRestController.class)
class QuestionRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetQuestionsByCategory() throws Exception {
        mockMvc.perform(get("/api/questions/category/Algebra"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetQuestionsByDifficulty() throws Exception {
        mockMvc.perform(get("/api/questions/difficulty/easy"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetRandomQuestion() throws Exception {
        mockMvc.perform(get("/api/questions/random"))
            .andExpect(status().isOk());
    }
}
