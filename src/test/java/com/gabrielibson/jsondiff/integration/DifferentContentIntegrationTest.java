package com.gabrielibson.jsondiff.integration;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

public class DifferentContentIntegrationTest extends AbstractIntegrationTest {

    @Test
    @Order(1)
    public void sendingDifferentDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(LEFT_API, ScenariosConstants.DIFFERENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_LEFT_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingDifferentDataToRightSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(RIGHT_API, ScenariosConstants.DIFFERENT_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_RIGHT_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(3)
    public void gettingDiffDifferentDataTest() throws Exception {
        String expectedJson = "{" +
                "\"status\":\"JSON files are different\"" +
                ",\"differences\":[" +
                "{\"initialOffset\":8,\"finalOffset\":13,\"length\":5}," +
                "{\"initialOffset\":14,\"finalOffset\":16,\"length\":2}," +
                "{\"initialOffset\":18,\"finalOffset\":20,\"length\":2}," +
                "{\"initialOffset\":27,\"finalOffset\":46,\"length\":19}]}";
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format(DIFF_API, ScenariosConstants.DIFFERENT_ID)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedJson, true))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
