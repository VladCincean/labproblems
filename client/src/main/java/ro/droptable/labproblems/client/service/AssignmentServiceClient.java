package ro.droptable.labproblems.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.service.AssignmentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by stefana on 3/28/2017.
 */
public class AssignmentServiceClient implements AssignmentService {

    @Autowired
    private AssignmentService assignmentService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAssignment(long studentId, long problemId) {
        assignmentService.addAssignment(studentId, problemId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAssignment(Long id) {
        assignmentService.deleteAssignment(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAssignment(Long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException {
        assignmentService.updateAssignment(id, studentId, problemId, grade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Assignment> findOneAssignment(Long id) {
        return assignmentService.findOneAssignment(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Assignment> findAllAssignments() {
        return assignmentService.findAllAssignments();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Assignment> filterAssignmentsByGrade(int grade) {
        return assignmentService.filterAssignmentsByGrade(grade);
    }
}
