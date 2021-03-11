package com.gabrielibson.jsondiff.enums;

/**
 * @author Gabriel Ibson
 */
public enum DiffStatus {
    EQUAL("JSON files are equals"),
    NOT_EQUAL_SIZES("JSON files has not the same size"),
    DIFFERENT("JSON files are different");

    private String status;

    DiffStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
