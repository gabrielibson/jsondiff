package com.gabrielibson.jsondiff.controller;

import com.gabrielibson.jsondiff.dto.DiffDTO;
import com.gabrielibson.jsondiff.exception.ElementNotFoundException;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.service.JsonDiffRestService;
import com.gabrielibson.jsondiff.service.JsonDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping(("/v1/diff"))
public class JsonDiffController {

    @Autowired
    private JsonDiffRestService restService;

    @Autowired
    private JsonDiffService diffService;

    @PostMapping(value = "/{id}/left")
    public ResponseEntity<Void> saveLeft(@PathVariable String id, @RequestBody byte[] data) {
        restService.saveLeft(id, getConvertedData(data));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/right")
    public ResponseEntity<Void> saveRight(@PathVariable String id, @RequestBody byte[] data) {
        restService.saveRight(id, getConvertedData(data));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDiff(@PathVariable String id) {
        Diff diff = restService.getById(id);
        return new ResponseEntity<>(new DiffDTO(diff.getStatus(), diff.getDifferences()), HttpStatus.OK);
    }

    private String getConvertedData(byte[] data) {
        return restService.convertByteArrayToString(data);
    }
}
