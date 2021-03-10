package com.gabrielibson.jsondiff.controller;

import com.gabrielibson.jsondiff.dto.ErrorResponseDTO;
import com.gabrielibson.jsondiff.exception.ElementNotFoundException;
import com.gabrielibson.jsondiff.exception.InvalidJsonFileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JsonDiffControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(JsonDiffControllerExceptionHandler.class);

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDTO handleElementNotFoundException(ElementNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidJsonFileFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO handleInvalidJsonFileFormatException(InvalidJsonFileFormatException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO buildErrorResponse(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(status.value())
                .message(e.getMessage())
                .build();
        logger.error(errorResponseDTO.getMessage(), errorResponseDTO);
        return errorResponseDTO;
    }
}
