package ro.droptable.labproblems.server.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/29/2017.
 */
public class StudentServiceImplTest {
    Repository<Long, Student> r;
    StudentService s;

    @Before
    public void setUp() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(
                1
        );

        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "admin";

        r = new StudentDbRepository(
                new StudentValidator(),
                url,
                username,
                password
        );

        s = new StudentServiceImpl(executorService, r);

        s.deleteStudent("1");
        s.deleteStudent("2");
        s.deleteStudent("3");
        s.addStudent("1,1,alice,222");
        s.addStudent("2,11,bab,222");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println(s.findAllStudents("").get());
        s.deleteStudent("1");
        s.deleteStudent("2");
        s.deleteStudent("3");
        System.out.println(s.findAllStudents("").get());
    }

    @Test
    public void addStudent() throws Exception {
        s.addStudent("3,3,alice,222");
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 3);
        s.addStudent("4,3,alice,200");
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 3);
    }

    @Test
    public void filterStudentsByName() throws Exception {
        assertTrue(Arrays.asList(s.filterStudentsByName("al").get().split("\n")).size() == 1);
        assertTrue(Arrays.asList(s.filterStudentsByName("a").get().split("\n")).size() == 2);
        assertTrue(Arrays.asList(s.filterStudentsByName("b").get().split("\n")).size() == 1);
        assertTrue(s.filterStudentsByName("x").get().length() == 0);
    }

    @Test
    public void updateStudent() throws Exception {
        s.updateStudent("1,1,alice,223");
        assertTrue(s.findOneStudent("1").get().equals("1,1,alice,223"));
        s.updateStudent("1,1,alice,0");

        assertTrue(s.findOneStudent("1").get().endsWith("223"));
    }

    @Test
    public void deleteStudent() throws Exception {
        System.out.println("ndel" + s.findAllStudents("").get());
        s.deleteStudent("1");
        System.out.println("del" + s.findAllStudents("").get());
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 1);
        s.deleteStudent("1");
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 1);
    }

    @Test
    public void findOneStudent() throws Exception {
        assertTrue(s.findOneStudent("1").get().equals("1,1,alice,222"));
    }

    @Test
    public void getAllStudents() throws Exception {
        s.addStudent("3,3,alice,222");
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 3);
        s.addStudent("4,3,alice,200");
        assertTrue(Arrays.asList(s.findAllStudents("").get().split("\n")).size() == 3);
    }
    @Test
    public void filterLargestGroup() throws Exception {
        s.addStudent("3,3,alice,222");
        assert(s.filterLargestGroup().get().equals("222"));
    }

}