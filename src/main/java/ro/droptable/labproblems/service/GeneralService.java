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
    //create
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
        studentService.add(serialNumber, name, group);
    }
    //read
    public Optional<Student> findOneStudentById(Long id){
        return studentService.findOne(id);
    }
    public Optional<Student> findOneStudentByAttributes(String serialNumber, String name, int group){
        return studentService.getByAttributes(serialNumber, name, group);
    }
    public Set<Student> findAllStudents(){
        return studentService.getAll();
    }
    //update
    public void updateStudent(long id, String serialNumber, String name, int group) throws NoSuchElementException, ValidatorException {
        studentService.update(id, serialNumber, name, group);
    }
    //delete
    public void deleteStudent(long id){
        studentService.delete(id);
        Set<Assignment> i = assignmentService.filterByStudent(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
    }

    //FILTER for Student
    public Set<Student> filterStudentsByName(String s) {
        return studentService.filterStudentsByName(s);
    }



    //CRUD for Problem
    //create
    public void addProblem(String title, String description) throws ValidatorException {
        problemService.add(title, description);
    }
    //read
    public Optional<Problem> findOneProblemById(Long id){
        return problemService.findOne(id);
    }
    public Optional<Problem> findOneProblemByAttributes(String title, String description){
        return problemService.getByAttributes(title, description);
    }
    public Set<Problem> findAllProblems(){
        return problemService.getAll();
    }
    //update
    public void updateProblem(long id, String title, String description) throws NoSuchElementException, ValidatorException {
        problemService.update(id, title, description);
    }
    //delete
    public void deleteProblem(long id){
        problemService.delete(id);
        Set<Assignment> i = assignmentService.filterByProblem(id);
        i.forEach(assignment -> assignmentService.delete(assignment.getId()));
    }




    //CRUD for Assignment
    //create
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
    //read
    public Optional<Assignment> findOneAssignmentById(Long id){
        return assignmentService.findOne(id);
    }
    public Optional<Assignment> findOneAssignmentByAttributes(long studentId, long problemId){
        return assignmentService.getByAttributes(studentId, problemId);
    }
    public Set<Assignment> findAllAssignments(){
        return assignmentService.getAll();
    }
    //update
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
    //delete
    public void deleteAssignment(long id){
        assignmentService.delete(id);
    }
}
