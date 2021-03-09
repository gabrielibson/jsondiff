package com.gabrielibson.jsondiff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * @author Gabriel Ibson
 */

@Data
@AllArgsConstructor
@Builder
public class Diff implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @NonNull
    private String id;
    private String left;
    private String right;
    private String status;
    private List<Difference> differences;
}
