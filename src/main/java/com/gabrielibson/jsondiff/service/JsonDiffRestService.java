package com.gabrielibson.jsondiff.service;

import com.gabrielibson.jsondiff.model.Diff;

public interface JsonDiffRestService {

    String convertByteArrayToString(byte[] data);
    Diff getById(String id);
    void saveLeft(String id, String data);
    void saveRight(String id, String data);
}
