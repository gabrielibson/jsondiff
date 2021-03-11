package com.gabrielibson.jsondiff.integration;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

public class DifferentSizeIntegrationTest extends AbstractIntegrationTest {

    @Test
    @Order(1)
    public void sendingDifferentSizeDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(LEFT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_LEFT_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingDifferentSizeDataToRightSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(RIGHT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_RIGHT_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(3)
    public void gettingDiffDifferentSizeTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format(DIFF_API, ScenariosConstants.DIFFERENT_SIZE_ID)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"status\" : \"JSON files are not the same size\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}