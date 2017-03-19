package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/11/2017.
 */
public class GeneralService {
    private StudentService studentService;
    private ProblemService problemService;
    private AssignmentService assignmentService;

    public GeneralService(StudentService studentService, ProblemService problemService, AssignmentService assignmentService) {
        this.studentService = studentService;
        this.problemService = problemService;
        this.assignmentService = assignmentService;
    }

    //CRUD for Student
    /**
     * Saves the given entity.
     *
     * @param serialNumber
     *            must not be null.
     * @param name
     *            must not be null.
     * @param group
     *
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
        studentService.add(serialNumber, name, group);
    }

    /**
     * Returns an optional containing the student with the given id if existent
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     */
    public Optional<Student> findOneStudentById(Long id){
        return studentService.findOne(id);
    }

    /**
     * Returns an optional containing the student with the given attributes if existent
     *
     * @param serialNumber
     *            must not be null.
     * @param name
     *            must not be null.
     * @param group
     * @return an {@code Optional} encapsulating the entity with the given attributes.
     */
    public Optional<Student> findOneStudentByAttributes(String serialNumber, String name, int group){
        return studentService.getByAttributes(serialNumber, name, group);
    }

    /**
     * Returns all students
     * @return set of (@code Student)
     */
    public Set<Student> findAllStudents(){
        return studentService.getAll();
    }

    /**
     * Updates the given entity.
     * @param id
     *
     * @param serialNumber
     *            must not be null.
     * @param name
     *            must not be null.
     * @param group
     *
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void updateStudent(long id, String serialNumber, String name, int group) throws NoSuchElementException, ValidatorException {
        studentService.update(id, serialNumber, name, group);
    }

    /**
     * Deletes the given entity.
     *
     * @param id
     */
    public void deleteStudent(long id){
        studentService.delete(id);
        Set<Assignment> i = assignmentService.filterByStudent(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
    }

    /**
     * Returns all students with the name containing the parameter
     * @param s
     *            must not be null.
     * @return set of (@code Student)
     */
    public Set<Student> filterStudentsByName(String s) {
        return studentService.filterStudentsByName(s);
    }

    public int filterLargestGroup() {
        return studentService.filterLargestGroup();
    }




    //CRUD for Problem
    /**
     * Saves the given entity.
     *
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void addProblem(String title, String description) throws ValidatorException {
        problemService.add(title, description);
    }

    /**
     * Returns an optional containing the problem with the given id if existent
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     */
    public Optional<Problem> findOneProblemById(Long id){
        return problemService.findOne(id);
    }

    /**
     * Returns an optional containing the problem with the given attributes if existent
     *
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @return an {@code Optional} encapsulating the entity with the given attributes.
     */
    public Optional<Problem> findOneProblemByAttributes(String title, String description){
        return problemService.getByAttributes(title, description);
    }

    /**
     * Returns all problems
     * @return set of (@code Problem)
     */
    public Set<Problem> findAllProblems(){
        return problemService.getAll();
    }


    /**
     * Updates the given entity.
     * @param id
     *
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void updateProblem(long id, String title, String description) throws NoSuchElementException, ValidatorException {
        problemService.update(id, title, description);
    }

    /**
     * Deletes the given entity.
     *
     * @param id
     */
    public void deleteProblem(long id){
        problemService.delete(id);
        Set<Assignment> i = assignmentService.filterByProblem(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
    }

    //CRUD for Assignment
    /**
     * Saves the given entity.
     * @param studentId
     *            must not be null.
     * @param problemId
     *            must not be null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void addAssignment(long studentId, long problemId) throws ValidatorException {
        Student student;
        try {
            student = studentService.findOne(studentId).get();
            Objects.requireNonNull(student);
        } catch (NullPointerException | NoSuchElementException e) {
            throw new ValidatorException("student not found");
        }

        Problem problem;
        try {
            problem = problemService.findOne(problemId).get();
            Objects.requireNonNull(problem);
        } catch (NullPointerException | NoSuchElementException e) {
            throw new ValidatorException("problem not found");
        }

        assignmentService.add(studentId, problemId);
    }

    /**
     * Returns an optional containing the assignment with the given id if existent
     *
     * @param id
     * @return an {@code Optional} encapsulating the entity with the given id.
     */
    public Optional<Assignment> findOneAssignmentById(Long id){
        return assignmentService.findOne(id);
    }

    /**
     * Returns an optional containing the assignment with the given attributes if existent
     *
     * @param studentId
     *            must not be null.
     * @param problemId
     *            must not be null.
     * @return an {@code Optional} encapsulating the entity with the given attributes.
     */
    public Optional<Assignment> findOneAssignmentByAttributes(long studentId, long problemId){
        return assignmentService.getByAttributes(studentId, problemId);
    }

    /**
     * Returns all entities
     *
     * @return set of {@code Assignment}
     */
    public Set<Assignment> findAllAssignments(){
        return assignmentService.getAll();
    }

    /**
     * Updates the given entity.
     * @param id
     *
     * @param studentId
     *            must not be null.
     * @param problemId
     *            must not be null.
     * @param grade
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void updateAssignment(long id, long studentId, long problemId, int grade) throws NoSuchElementException, ValidatorException {
        Student student;
        try {
            student = studentService.findOne(studentId).get();
            Objects.requireNonNull(student);
        } catch (NullPointerException | NoSuchElementException e) {
            throw new ValidatorException("student not found");
        }

        Problem problem;
        try {
            problem = problemService.findOne(problemId).get();
            Objects.requireNonNull(problem);
        } catch (NullPointerException | NoSuchElementException e) {
            throw new ValidatorException("problem not found");
        }

        assignmentService.update(id, studentId, problemId, grade);
    }

    /**
     * Deletes the given entity.
     *
     * @param id
     */
    public void deleteAssignment(long id){
        assignmentService.delete(id);
    }
}
