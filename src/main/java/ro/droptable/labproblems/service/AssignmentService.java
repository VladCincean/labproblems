package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.repository.Repository;

/**
 * Created by stefana on 3/5/2017.
 */
public class AssignmentService extends Service<Assignment> {
    public AssignmentService(Repository<Long, Assignment > repository) {
        this.repository = repository;
    }
}
