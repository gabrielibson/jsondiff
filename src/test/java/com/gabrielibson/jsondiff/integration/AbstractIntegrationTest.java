package com.gabrielibson.jsondiff.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractIntegrationTest {

    protected static final String RIGHT_API = "/v1/diff/%s/right";
    protected static final String LEFT_API = "/v1/diff/%s/left";
    protected static final String DIFF_API = "/v1/diff/%s";

    @Autowired
    protected MockMvc mockMvc;
}
