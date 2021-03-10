package com.gabrielibson.jsondiff.controller;

import com.gabrielibson.jsondiff.exception.ElementNotFoundException;
import com.gabrielibson.jsondiff.service.JsonDiffRestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
public class JsonDiffControllerTest {

    @Autowired
    private JsonDiffController controller;

    private JsonDiffRestService restService = Mockito.mock(JsonDiffRestService.class);

    private static final String ID = "Id";
    private static final String text = "example text";
    private static final byte[] data = Base64.getMimeEncoder().encode(text.getBytes(StandardCharsets.UTF_8));

    @BeforeEach
    public void setup() {
        controller = new JsonDiffController(restService);
    }

    @Test
    public void testIdNotFound() {
        Mockito.when(restService.getById(ArgumentMatchers.isNull()))
                .thenThrow(ElementNotFoundException.class);

        Assertions.assertThrows(ElementNotFoundException.class,
                () -> controller.getDiff(null));
    }

    @Test
    public void testLeftJsonFileNull() {
        Mockito.when(restService.convertByteArrayToString(ArgumentMatchers.isNull()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.saveLeft(ID, null));
    }

    @Test
    public void testRightJsonFileNull() {
        Mockito.when(restService.convertByteArrayToString(ArgumentMatchers.isNull()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.saveRight(ID, null));
    }

    @Test
    public void testSaveLeftSuccessfully() {
        Mockito.when(restService.convertByteArrayToString(ArgumentMatchers.any()))
                .thenReturn(text);

        ResponseEntity<Void> response = controller.saveLeft(ID, data);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testSaveRightSuccessfully() {
        Mockito.when(restService.convertByteArrayToString(ArgumentMatchers.any()))
                .thenReturn(text);

        ResponseEntity<Void> response = controller.saveRight(ID, data);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
}
