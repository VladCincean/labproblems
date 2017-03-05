package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.repository.Repository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 05.03.2017.
 */
public class ProblemService extends Service<Problem> {
    public ProblemService(Repository<Long, Problem > repository) {
        this.repository = repository;
    }
}
