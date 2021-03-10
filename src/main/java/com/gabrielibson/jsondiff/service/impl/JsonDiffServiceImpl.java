package com.gabrielibson.jsondiff.service.impl;

import com.gabrielibson.jsondiff.enums.DiffStatus;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.model.Difference;
import com.gabrielibson.jsondiff.repository.JsonDiffRepository;
import com.gabrielibson.jsondiff.service.JsonDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Ibson
 */

@Service
public class JsonDiffServiceImpl implements JsonDiffService {

    @Autowired
    JsonDiffRepository repository;

    public Diff processDiff(Diff diffToBeProcessed) {
        Diff.DiffBuilder diffBuilder = Diff.builder()
                .id(diffToBeProcessed.getId())
                .left(diffToBeProcessed.getLeft())
                .right(diffToBeProcessed.getRight());

        if(diffToBeProcessed.getLeft().equals(diffToBeProcessed.getRight())){
            diffBuilder.status(DiffStatus.EQUAL.getStatus());
            return repository.save(diffBuilder.build());
        }
        if(diffToBeProcessed.getLeft().length() != diffToBeProcessed.getRight().length()){
            diffBuilder.status(DiffStatus.NOT_EQUAL_SIZES.getStatus());
            return repository.save(diffBuilder.build());
        }

        long initialOffset = 0;
        long length = 0;
        boolean firstTime = true;
        boolean isDifferent = false;

        List<Difference> differences = new ArrayList<>();

        for(int i = 0; i < diffToBeProcessed.getLeft().length(); i++) {
            if(diffToBeProcessed.getLeft().charAt(i) != diffToBeProcessed.getRight().charAt(i)) {
                initialOffset = firstTime? i : initialOffset;
                length++;
                firstTime = false;
                isDifferent = true;
                continue;
            }
            if(isDifferent) {
                this.addDifference(initialOffset, length, differences);
                length = 0;
                firstTime = true;
                isDifferent = false;
            }
        }

        if(isDifferent){
            this.addDifference(initialOffset, length, differences);
        }

        diffBuilder.status(DiffStatus.DIFFERENT.getStatus());
        diffBuilder.differences(differences);

        return repository.save(diffBuilder.build());
    }

    private void addDifference(long initialOffset, long length, List<Difference> differences) {
        Difference difference = new Difference(initialOffset, initialOffset + length, length);
        differences.add(difference);
    }
}
