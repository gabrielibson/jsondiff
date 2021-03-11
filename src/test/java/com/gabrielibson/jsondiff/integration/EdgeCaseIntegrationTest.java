package com.gabrielibson.jsondiff.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class EdgeCaseIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void gettingDiffOfNonExistentIdTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format(DIFF_API, "abc")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"status\":404,\"message\":\"Element not found for id abc\"}", true));
    }

    @Test
    public void gettingNotFoundOnInvalidUrl() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/non-existent/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void gettingMethodNotAllowedTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format(LEFT_API, "any")))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}
