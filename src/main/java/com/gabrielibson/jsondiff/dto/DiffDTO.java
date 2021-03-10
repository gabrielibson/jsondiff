package com.gabrielibson.jsondiff.dto;

import com.gabrielibson.jsondiff.model.Difference;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiffDTO {

    private String status;
    private List<Difference> differences;
}
