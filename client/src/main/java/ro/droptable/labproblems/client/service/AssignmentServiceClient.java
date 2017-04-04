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

    @Override
    public void addAssignment(long studentId, long problemId) {
        assignmentService.addAssignment(studentId, problemId);
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentService.deleteAssignment(id);
    }

    @Override
    public void updateAssignment(long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException {
        assignmentService.updateAssignment(id, studentId, problemId, grade);
    }

    @Override
    public Optional<Assignment> findOneAssignment(Long id) {
        return assignmentService.findOneAssignment(id);
    }

    @Override
    public Set<Assignment> findAllAssignments() {
        return assignmentService.findAllAssignments();
    }

    @Override
    public Set<Assignment> filterAssignmentsByGrade(int g) {
        return assignmentService.filterAssignmentsByGrade(g);
    }
}
