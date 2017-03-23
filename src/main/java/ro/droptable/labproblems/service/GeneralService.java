package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.util.*;
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
        Set<Assignment> i = assignmentService.filterByStudent(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
        studentService.delete(id);
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
        Set<Assignment> i = assignmentService.filterByProblem(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
        problemService.delete(id);
    }

    public Set<Problem> filterProblemsByName(String s) {
        return problemService.filterProblemsByName(s);
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

    public Set<Assignment> filterAssignmentsByGrade(int g) {
        return assignmentService.filterAssignmentsByGrade(g);
    }
    public Map<Student, Double> reportStudentAverage(){
        HashMap<Student, Double> mp = new HashMap<>();
        HashMap<Student, Double> nop = new HashMap<>();
        Iterable<Student> students = studentService.getAll();
        students.forEach(s -> {mp.put(s, 0.0); nop.put(s, 0.0);});
        Iterable<Assignment> assignments = assignmentService.getAll();
        assignments.forEach(assignment -> {
            mp.put(findOneStudentById(assignment.getStudentId()).get(), mp.get(findOneStudentById(assignment.getStudentId()).get()) + assignment.getGrade());
            nop.put(findOneStudentById(assignment.getStudentId()).get(), nop.get(findOneStudentById(assignment.getStudentId()).get()) + 1.0);
        });

        students.forEach(s -> mp.put(s, mp.get(s)/nop.get(s)));
        return mp;
    }
}
