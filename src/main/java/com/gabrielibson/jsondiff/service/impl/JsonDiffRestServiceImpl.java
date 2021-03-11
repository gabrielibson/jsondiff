package com.gabrielibson.jsondiff.service.impl;

import com.gabrielibson.jsondiff.enums.DiffSide;
import com.gabrielibson.jsondiff.exception.ElementNotFoundException;
import com.gabrielibson.jsondiff.exception.InvalidJsonFileFormatException;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.repository.JsonDiffRepository;
import com.gabrielibson.jsondiff.service.JsonDiffRestService;
import com.gabrielibson.jsondiff.service.JsonDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class JsonDiffRestServiceImpl implements JsonDiffRestService {

    @Autowired
    private JsonDiffRepository repository;

    @Autowired
    private JsonDiffService diffService;

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
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("ID should not be blank");
        }
        return this.repository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public void saveLeft(String id, String data) {
        try {
            Diff diff = this.getById(id);
            Diff diffToBeSaved = Diff.builder()
                    .id(diff.getId())
                    .left(data)
                    .right(diff.getRight())
                    .build();
            this.save(diffToBeSaved, DiffSide.LEFT);
        }catch(ElementNotFoundException enf) {
            Diff diff = Diff.builder().id(id).left(data).build();
            this.save(diff, DiffSide.LEFT);
        }
    }

    @Override
    public void saveRight(String id, String data) {
        try {
            Diff diff = this.getById(id);
            Diff diffToBeSaved = Diff.builder()
                    .id(diff.getId())
                    .left(diff.getLeft())
                    .right(data)
                    .build();
            this.save(diffToBeSaved, DiffSide.RIGHT);
        }catch(ElementNotFoundException enf) {
            Diff diff = Diff.builder().id(id).right(data).build();
            this.save(diff, DiffSide.RIGHT);
        }
    }

    private void save(Diff diff, DiffSide side) {
        Diff savedDiff = this.repository.save(diff);
        if(side.equals(DiffSide.RIGHT) && savedDiff.getLeft() != null
            || side.equals(DiffSide.LEFT) && savedDiff.getRight() != null) {
            this.repository.save(this.diffService.processDiff(savedDiff));
        }
    }
}
