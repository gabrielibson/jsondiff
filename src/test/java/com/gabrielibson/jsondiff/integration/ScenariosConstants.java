package com.gabrielibson.jsondiff.integration;

public final class ScenariosConstants {

    public static final String EQUAL_ID = "equal";
    public static final String EQUAL_DATA = "{\"key\":\"equal value for the side\"}";

    public static final String DIFFERENT_SIZE_ID = "size";
    public static final String DIFFERENT_SIZE_LEFT_DATA = "{\"key\":\"different size value for the left side\"}";
    public static final String DIFFERENT_SIZE_RIGHT_DATA = "{\"key\":\"different size for the right\"}";

    public static final String DIFFERENT_ID = "diff";
    public static final String DIFFERENT_LEFT_DATA = "{\"key\":\"words are the same but different order\"}";
    public static final String DIFFERENT_RIGHT_DATA = "{\"key\":\"order the are same different words but\"}";
}
