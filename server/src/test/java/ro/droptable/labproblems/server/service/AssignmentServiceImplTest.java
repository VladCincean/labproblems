package ro.droptable.labproblems.server.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.common.service.AssignmentService;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.server.repository.AssignmentDbRepository;
import ro.droptable.labproblems.server.repository.ProblemDbRepository;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/28/2017.
 */
public class AssignmentServiceImplTest {
    AssignmentDbRepository r;
    StudentService ss;
    ProblemService ps;
    AssignmentService s;
    Student st;
    Problem pr;

    @Before
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "admin";

        ExecutorService executorService = Executors.newFixedThreadPool(
                1
        );

        Repository<Long, Student> studentRepository = new StudentDbRepository(
                new StudentValidator(),
                url,
                username,
                password
        );

        ss = new StudentServiceImpl(executorService, studentRepository);

        Repository<Long, Problem> problemRepository = new ProblemDbRepository(
                new ProblemValidator(),
                url,
                username,
                password
        );

        ps = new ProblemServiceImpl(executorService, problemRepository);

        r = new AssignmentDbRepository(
                new AssignmentValidator(),
                url,
                username,
                password
        );

        s = new AssignmentServiceImpl(executorService, r);

        s.deleteAssignment("1");
        s.deleteAssignment("2");
        ss.deleteStudent("1");
        ss.deleteStudent("2");
        ps.deleteProblem("1");
        ps.deleteProblem("2");
        ss.addStudent("1,1,alice,222");
        ss.addStudent("2,2,bob,222");
        ps.addProblem("1,bla,blabla");
        ps.addProblem("2,w,hy");
        s.addAssignment("1,1,1");
    }


    @After
    public void tearDown() throws Exception {
        Future<String> crt = s.findAllAssignments("");
        StreamSupport.stream(Arrays.asList(crt.get().split("\n")).spliterator(), false).
                forEach(a -> {
                    //System.out.println(Arrays.asList(a.split(",")).get(0));
                    s.deleteAssignment(Arrays.asList(a.split(",")).get(0));
                        });

        Future<String> scrt = ss.findAllStudents("");
        StreamSupport.stream(Arrays.asList(scrt.get().split("\n")).spliterator(), false).
                forEach(a -> {
                    //System.out.println(Arrays.asList(a.split(",")).get(0));
                    ss.deleteStudent(Arrays.asList(a.split(",")).get(0));
                });

        Future<String> pcrt = ps.findAllProblems("");
        StreamSupport.stream(Arrays.asList(pcrt.get().split("\n")).spliterator(), false).
                forEach(a -> {
                    //System.out.println(Arrays.asList(a.split(",")).get(0));
                    ps.deleteProblem(Arrays.asList(a.split(",")).get(0));
                });
    }

    @Test
    public void addAssignment() throws Exception {

        System.out.println(s.findAllAssignments("").get());
        assertTrue(Arrays.asList(s.findAllAssignments("").
                get().split("\n")).size() == 1);
    }

    @Test
    public void delete() throws Exception {
        System.out.println(s.findAllAssignments("").
                get() + s.findAllAssignments("").
                get().length());
        s.deleteAssignment("1");
        s.deleteAssignment("1");
        System.out.println(s.findAllAssignments("").
                get() + s.findAllAssignments("").
                get().length());
        assertTrue(s.findAllAssignments("").
                get().length() == 0);

    }

    @Test
    public void findOne() throws Exception {
        String a = s.findOneAssignment("1").get();
        System.out.println("a" + a);
        assertTrue(a.equals("1,1,1,0.0"));
    }

    @Test
    public void getAll() throws Exception {
        assertTrue(s.findAllAssignments("").
                get().length() == 10);
    }


}