package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.repository.Repository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 05.03.2017.
 */
public class ProblemService extends Service {
    public ProblemService(Repository<Long, BaseEntity<Long> > repository) {
        this.repository = repository;
    }
}
