package com.gabrielibson.jsondiff.service.impl;

import com.gabrielibson.jsondiff.exception.ElementNotFoundException;
import com.gabrielibson.jsondiff.exception.InvalidJsonFileFormatException;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.repository.JsonDiffRepository;
import com.gabrielibson.jsondiff.service.JsonDiffRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JsonDiffRestServiceImpl implements JsonDiffRestService {

    @Autowired
    private JsonDiffRepository repository;

    @Override
    public String convertByteArrayToString(byte[] data) {
        try {
            return new String(Base64.getDecoder().decode(data));
        }catch(IllegalArgumentException iae) {
            throw new InvalidJsonFileFormatException();
        }
    }

    @Override
    public Diff getById(String id) {
        if(id.isBlank()){
            throw new IllegalArgumentException("ID should not be blank");
        }

        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public void saveLeft(String id, String data) {
        Diff.DiffBuilder diffBuilder = Diff.builder().id(id).left(data);
        repository.save(diffBuilder.build());
    }

    @Override
    public void saveRight(String id, String data) {
        Diff.DiffBuilder diffBuilder = Diff.builder().id(id).right(data);
        repository.save(diffBuilder.build());
    }
}
