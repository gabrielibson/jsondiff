package com.gabrielibson.jsondiff.exception;

import java.util.NoSuchElementException;

/**
 * Class represents an exception when is not possible to find
 * a Diff for a specific ID
 *
 * @autor Gabriel Ibson
 */
public class ElementNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Element not found for id %s";

    public ElementNotFoundException(String id) {
        super(String.format(MESSAGE, id));
    }
}
