package com.gabrielibson.jsondiff.service.impl;

import com.gabrielibson.jsondiff.enums.DiffStatus;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.model.Difference;
import com.gabrielibson.jsondiff.service.JsonDiffService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class hold business logic regarding to Diff processing
 *
 * @author Gabriel Ibson
 */

@Service
public class JsonDiffServiceImpl implements JsonDiffService {

    /**
     * Process diff to get differences between left and right sides
     * @param diffToBeProcessed - diff to be processed
     * @return diff result
     */
    public Diff processDiff(Diff diffToBeProcessed) {
        Diff.DiffBuilder diffBuilder = Diff.builder()
                .id(diffToBeProcessed.getId())
                .left(diffToBeProcessed.getLeft())
                .right(diffToBeProcessed.getRight());

        if (isEqualOrNotOfEqualSizes(diffToBeProcessed, diffBuilder)) return diffBuilder.build();

        List<Difference> differences = getDifferences(diffToBeProcessed);

        diffBuilder.status(DiffStatus.DIFFERENT.getStatus());
        diffBuilder.differences(differences);

        return diffBuilder.build();
    }

    /**
     * Create new Difference and add to the list
     * @param initialOffset - init point of difference
     * @param length - difference length
     * @param differences - differences list
     */
    private void addDifference(long initialOffset, long length, List<Difference> differences) {
        Difference difference = new Difference(initialOffset, initialOffset + length, length);
        differences.add(difference);
    }

    /**
     * Calculate differences between left and right
     * @param diffToBeProcessed - diff to be processed
     * @return List of differences
     */
    private List<Difference> getDifferences(Diff diffToBeProcessed) {
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
        return differences;
    }

    /**
     * If left and right side are equal, return that;
     * If left and right side are not same size, return that;
     * @param diffToBeProcessed - diff to be processed
     * @param diffBuilder - builder to aggregate status
     * @return true if content are equals or if they are not of equal sizes, false otherwise
     */
    private boolean isEqualOrNotOfEqualSizes(Diff diffToBeProcessed, Diff.DiffBuilder diffBuilder) {
        if(diffToBeProcessed.getLeft().equals(diffToBeProcessed.getRight())){
            diffBuilder.status(DiffStatus.EQUAL.getStatus());
            return true;
        }
        if(diffToBeProcessed.getLeft().length() != diffToBeProcessed.getRight().length()){
            diffBuilder.status(DiffStatus.NOT_EQUAL_SIZES.getStatus());
            return true;
        }
        return false;
    }
}
