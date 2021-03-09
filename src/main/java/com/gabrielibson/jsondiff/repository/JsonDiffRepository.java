package com.gabrielibson.jsondiff.repository;

import com.gabrielibson.jsondiff.model.Diff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gabriel Ibson
 */

@Repository
public interface JsonDiffRepository extends CrudRepository<Diff, String> {
}
