package ro.droptable.labproblems.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.repository.InMemoryRepository;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/7/2017.
 */
public class AssignmentServiceTest {
    InMemoryRepository<Long, Assignment> r = new InMemoryRepository<Long, Assignment>(new AssignmentValidator());
    StudentService ss = new StudentService(new InMemoryRepository<Long, Student>(new StudentValidator()));
    ProblemService ps = new ProblemService(new InMemoryRepository<Long, Problem>(new ProblemValidator()));
    AssignmentService s = new AssignmentService(r, ss, ps);

    @Before
    public void setUp() throws Exception {
        ss.add("1","alice", 222);
        ss.add("2", "bob", 222);
        ps.add("bla", "blabla");
        ps.add("w", "hy");
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

}