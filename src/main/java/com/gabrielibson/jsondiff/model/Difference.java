package com.gabrielibson.jsondiff.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Gabriel Ibson
 */

@Data
@AllArgsConstructor
public class Difference {

    private long initialOffset;
    private long finalOffset;
    private long length;
}
