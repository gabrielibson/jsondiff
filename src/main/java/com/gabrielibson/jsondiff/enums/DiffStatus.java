package com.gabrielibson.jsondiff.enums;

public enum DiffStatus {
    EQUAL("JSON files are equals"),
    NOT_EQUAL_SIZES("JSON files has not the same size"),
    DIFFERENT("JSON files are different"),
    NOT_EVALUATED("JSON files not evaluated yet");

    private String status;

    DiffStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
