package ro.droptable.labproblems.server.service;

import ro.droptable.labproblems.common.AssignmentService;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.Repository;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/28/2017.
 */
public class AssignmentServiceImpl implements AssignmentService{
    private ExecutorService executorService;
    private Repository<Long, Assignment> assignmentRepository;

    public AssignmentServiceImpl(ExecutorService executorService, Repository<Long, Assignment> assignmentRepository) {
        this.executorService = executorService;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Future<String> addAssignment(String string) throws ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            Long studentId = Long.valueOf(fields.get(1));
            Long problemId = Long.valueOf(fields.get(2));

            Assignment assignment = new Assignment(id, studentId, problemId);

            Optional<Assignment> optional = assignmentRepository.save(assignment);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> deleteAssignment(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Assignment> optional = assignmentRepository.delete(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> updateAssignment(String string) throws NoSuchElementException, ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            Long studentId = Long.valueOf(fields.get(1));
            Long problemId = Long.valueOf(fields.get(2));
            double grade = Double.valueOf(fields.get(3));

            // throws NoSuchElementException if the old Assignment does not exist
            Assignment oldAssignment = assignmentRepository.findOne(Long.valueOf(id)).get();

            //TODO check if studentId and problemId belong to the repos
            Assignment newAssignment = new Assignment(
                    id,
                    studentId,
                    problemId
            );
            grade = (grade < 1.0 || grade > 10.0) ? oldAssignment.getGrade() : grade;
            newAssignment.setGrade(grade);
            Optional<Assignment> optional = assignmentRepository.update(newAssignment);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findOneAssignment(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Assignment> optional = assignmentRepository.findOne(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findAllAssignments(String string) {
        return executorService.submit(() -> {
            Iterable<Assignment> allAssignments = assignmentRepository.findAll();

            return StreamSupport.stream(allAssignments.spliterator(), false)
                    .map(Assignment::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }

    @Override
    public Future<String> filterAssignmentsByGrade(String string) {
        return executorService.submit(() -> {
            Iterable<Assignment> allAssignments = assignmentRepository.findAll();
            double grade = Double.valueOf(string);
            return StreamSupport.stream(allAssignments.spliterator(), false)
                    .filter(a -> a.getGrade() == grade)
                    .map(Assignment::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }
}
