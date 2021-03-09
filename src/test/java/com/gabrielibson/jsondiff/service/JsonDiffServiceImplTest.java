package com.gabrielibson.jsondiff.service;

import com.gabrielibson.jsondiff.enums.DiffStatus;
import com.gabrielibson.jsondiff.model.Diff;
import com.gabrielibson.jsondiff.model.Difference;
import com.gabrielibson.jsondiff.repository.JsonDiffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gabriel Ibson
 */

@SpringBootTest
public class JsonDiffServiceImplTest {

    public static final String ID = "Id";

    @Autowired
    private JsonDiffServiceImpl diffService;

    private JsonDiffRepository repository = Mockito.mock(JsonDiffRepository.class);

    @Test
    public void diffBetweenEquals() {
        String something = "Something";
        Diff diff = diffService.processDiff(ID, something, something);

        Assertions.assertEquals(DiffStatus.EQUAL.getStatus(), diff.getStatus());
    }

    @Test
    public void diffBetweenDifferentSizes() {
        String left = "Something";
        String right = "Something else";
        Diff diff = diffService.processDiff(ID, left, right);

        Assertions.assertEquals(DiffStatus.NOT_EQUAL_SIZES.getStatus(), diff.getStatus());
    }

    @Test
    public void diffBetweenSameSizeAndNotEquals() {
        String left = "SomethingElse";
        String right = "SomethingAbcd";

        Diff diff = diffService.processDiff(ID, left, right);

        List<Difference> differences = Arrays.asList(new Difference(9, 9 + 4, 4));

        Diff expected = Diff.builder().id(ID)
                .left(left).right(right).status(DiffStatus.DIFFERENT.getStatus())
                .differences(differences)
                .build();

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(expected);

        Difference differenceExpected = expected.getDifferences().get(0);
        Difference differenceResult = diff.getDifferences().get(0);

        Assertions.assertEquals(ID, diff.getId());
        Assertions.assertEquals(DiffStatus.DIFFERENT.getStatus(), diff.getStatus());
        Assertions.assertEquals(1, diff.getDifferences().size());
        Assertions.assertEquals(differenceExpected.getInitialOffset(), differenceResult.getInitialOffset());
        Assertions.assertEquals(differenceExpected.getFinalOffset(), differenceResult.getFinalOffset());
        Assertions.assertEquals(differenceExpected.getLength(), differenceResult.getLength());
    }
}
