package com.gabrielibson.jsondiff.integration;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

public class EqualDataIntegrationTest extends AbstractIntegrationTest {

    @Test
    @Order(1)
    public void sendingEqualDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(LEFT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingEqualDataToRightSideTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(String.format(RIGHT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(3)
    public void gettingDiffEqualTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format(DIFF_API, ScenariosConstants.EQUAL_ID)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"status\" : \"JSON files are equals\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
