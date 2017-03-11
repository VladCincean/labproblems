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
    Student st;
    Problem pr;

    @Before
    public void setUp() throws Exception {
        r = new InMemoryRepository<>(new AssignmentValidator());
        ss = new StudentService(new InMemoryRepository<>(new StudentValidator()));
        ps = new ProblemService(new InMemoryRepository<>(new ProblemValidator()));
        s = new AssignmentService(r);

        ss.add("1","alice", 222);
        ss.add("2", "bob", 222);
        ps.add("bla", "blabla");
        ps.add("w", "hy");

        st = ss.getByAttributes("1", "alice", 222).get();
        pr = ps.getByAttributes("bla", "blabla").get();

        s.add(st.getId(), pr.getId());
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {

        s.add(st.getId(), pr.getId());
        assertTrue(s.getAll().size() == 1);
    }

    @Test
    public void delete() throws Exception {
        long id = s.getByAttributes(st.getId(), pr.getId()).get().getId();
        s.delete(id);
        s.delete(id);
        assertTrue(s.getAll().size() == 0);

    }

    @Test
    public void findOne() throws Exception {
        long id = s.getByAttributes(st.getId(), pr.getId()).get().getId();
        Assignment a = s.findOne(id).get();
        assertTrue(a.getProblemId() == pr.getId());
    }

    @Test
    public void getAll() throws Exception {
        assertTrue(s.getAll().size() == 1);
    }

}