package ro.droptable.labproblems.server.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/28/2017.
 */
public class AssignmentDbRepositoryTest {

    String url = "jdbc:postgresql://localhost:5432/test";
    String username = "postgres";
    String password = "admin";

    Assignment a = new Assignment(1L, 1L);
    AssignmentDbRepository assignmentDbRepository;
    StudentDbRepository studentDbRepository;
    ProblemDbRepository problemDbRepository;

    @Before
    public void setUp() throws Exception {
        studentDbRepository = new StudentDbRepository(new StudentValidator(), url, username, password);
        problemDbRepository = new ProblemDbRepository(new ProblemValidator(), url, username, password);
        assignmentDbRepository = new AssignmentDbRepository(new AssignmentValidator(), url, username, password);

        Iterable<Assignment> crt = assignmentDbRepository.findAll();
        crt.forEach(s -> assignmentDbRepository.delete(s.getId()));

        Iterable<Student> scrt = studentDbRepository.findAll();
        scrt.forEach(s -> studentDbRepository.delete(s.getId()));

        Iterable<Problem> pcrt = problemDbRepository.findAll();
        pcrt.forEach(s -> problemDbRepository.delete(s.getId()));

        Student s1 = new Student("1", "1", 111);
        s1.setId(1L);
        Student s2 = new Student("2", "1", 111);
        s2.setId(2L);
        Problem p1 = new Problem("a", "a");
        p1.setId(1L);

        studentDbRepository.save(s1);
        studentDbRepository.save(s2);
        problemDbRepository.save(p1);
        assignmentDbRepository.save(a);
    }

    @After
    public void tearDown(){
        Iterable<Assignment> crt = assignmentDbRepository.findAll();
        crt.forEach(s -> assignmentDbRepository.delete(s.getId()));

        Iterable<Student> scrt = studentDbRepository.findAll();
        scrt.forEach(s -> studentDbRepository.delete(s.getId()));

        Iterable<Problem> pcrt = problemDbRepository.findAll();
        pcrt.forEach(s -> problemDbRepository.delete(s.getId()));
    }
    @Test
    public void findOne() throws Exception {
        assertTrue(assignmentDbRepository.findOne(a.getId()).get().equals(a));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(assignmentDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 1);
    }

    @Test
    public void save() throws Exception {
        Assignment a2 = new Assignment(2L, 2L);
        Problem p2 = new Problem("b", "b");
        p2.setId(2L);
        problemDbRepository.save(p2);
        assignmentDbRepository.save(a2);
        assertTrue(StreamSupport.stream(assignmentDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void delete() throws Exception {
        assignmentDbRepository.delete(a.getId());
        assertTrue(StreamSupport.stream(assignmentDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 0);
    }

    @Test
    public void update() throws Exception {
        Assignment nota = new Assignment(a.getStudentId()+1, a.getProblemId());
        nota.setId(a.getId());
        assignmentDbRepository.update(nota);
        assertTrue(assignmentDbRepository.findOne(nota.getId()).get().getStudentId() == a.getStudentId() + 1);
    }

}