package com.gabrielibson.jsondiff.exception;

import java.util.NoSuchElementException;

/**
 * Class represents an exception when the data informed is not
 * a valid Base64 format
 *
 * @author Gabriel Ibson
 */
public class InvalidJsonFileFormatException extends NoSuchElementException {

    private static final String MESSAGE = "Json file informed is not a valid Base64 encoded file";

    public InvalidJsonFileFormatException() {
        super(MESSAGE);
    }
}
