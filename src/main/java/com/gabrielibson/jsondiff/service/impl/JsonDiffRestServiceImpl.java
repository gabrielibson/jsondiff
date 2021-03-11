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

/**
 * Class to hold business logic regarding to rest operations
 *
 * @author Gabriel Ibson
 */

@Service
public class JsonDiffRestServiceImpl implements JsonDiffRestService {

    @Autowired
    private JsonDiffRepository repository;

    @Autowired
    private JsonDiffService diffService;

    /**
     * Convert Base64 encoded byte array into string format
     * @param data - Data to be converted
     * @return string result
     */
    @Override
    public String convertByteArrayToString(byte[] data) {
        try {
            return new String(Base64.getDecoder().decode(data));
        }catch(IllegalArgumentException iae) {
            throw new InvalidJsonFileFormatException();
        }
    }

    /**
     * Get diff by json file id
     * @param id - json file id
     * @return Diff - Document element from data source
     * @throws ElementNotFoundException - if id is not found
     */
    @Override
    public Diff getById(String id) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("ID should not be blank");
        }
        return this.repository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    /**
     * Save json data left side
     * @param id - json file id
     * @param data - Base64 encoded json file content
     */
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

    /**
     * Save json data right side
     * @param id - json file id
     * @param data - Base64 encoded json file content
     */
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

    /**
     * Verify both sides are informed, if so, process diff between them and save diff result
     * @param diff - Diff to be saved and processed
     * @param side - Specify which side is being saved
     */
    private void save(Diff diff, DiffSide side) {
        Diff savedDiff = this.repository.save(diff);
        if(side.equals(DiffSide.RIGHT) && savedDiff.getLeft() != null
            || side.equals(DiffSide.LEFT) && savedDiff.getRight() != null) {
            this.repository.save(this.diffService.processDiff(savedDiff));
        }
    }
}
