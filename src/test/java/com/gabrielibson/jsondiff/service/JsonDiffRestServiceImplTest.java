package com.gabrielibson.jsondiff.service;

import com.gabrielibson.jsondiff.exception.InvalidJsonFileFormatException;
import com.gabrielibson.jsondiff.repository.JsonDiffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
public class JsonDiffRestServiceImplTest {

    @SpyBean
    private JsonDiffRestService restService;

    private JsonDiffService diffService = Mockito.mock(JsonDiffService.class);
    private JsonDiffRepository repository = Mockito.mock(JsonDiffRepository.class);

    private static final String ID = "Id";
    private static final String LEFT = "Some data";
    private static final String RIGHT = LEFT;
    private static final String text = "example text";
    private static final byte[] data = Base64.getMimeEncoder().encode(text.getBytes(StandardCharsets.UTF_8));


    @Test
    public void testConvertByteArrayToStringWithInvalidData() {
        Assertions.assertThrows(InvalidJsonFileFormatException.class,
                () -> restService.convertByteArrayToString(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testConvertByteArrayToStringSuccessfully() {
        String convertedData = restService.convertByteArrayToString(data);
        Assertions.assertNotNull(convertedData);
        Assertions.assertEquals(text, convertedData);
    }

    @Test
    public void testIdNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restService.getById(null));
    }

    @Test
    public void testSaveLeftSuccessfully() {
        restService.saveRight(ID, LEFT);
        Mockito.verify(restService).saveRight(ID, LEFT);
    }

    @Test
    public void testSaveRightSuccessfully() {
        restService.saveRight(ID, RIGHT);
        Mockito.verify(restService).saveRight(ID, RIGHT);
    }

}
