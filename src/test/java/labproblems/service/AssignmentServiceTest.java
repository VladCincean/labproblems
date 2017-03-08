package labproblems.service;

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
import ro.droptable.labproblems.service.AssignmentService;
import ro.droptable.labproblems.service.ProblemService;
import ro.droptable.labproblems.service.StudentService;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/7/2017.
 */
public class AssignmentServiceTest {
    InMemoryRepository<Long, Assignment> r;
    StudentService ss;
    ProblemService ps;
    AssignmentService s;

    @Before
    public void setUp() throws Exception {
        r = new InMemoryRepository<>(new AssignmentValidator());
        ss = new StudentService(new InMemoryRepository<>(new StudentValidator()));
        ps = new ProblemService(new InMemoryRepository<>(new ProblemValidator()));
        s = new AssignmentService(r, ss, ps);

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