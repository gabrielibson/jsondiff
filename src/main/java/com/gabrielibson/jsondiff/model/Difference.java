package com.gabrielibson.jsondiff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Gabriel Ibson
 */

@Document
@Data
@AllArgsConstructor
public class Difference {

    private long initialOffset;
    private long finalOffset;
    private long length;
}
