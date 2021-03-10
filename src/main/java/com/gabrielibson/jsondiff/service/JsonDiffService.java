package com.gabrielibson.jsondiff.service;

import com.gabrielibson.jsondiff.model.Diff;

public interface JsonDiffService {

    Diff processDiff(Diff diffToBeProcessed);
}
