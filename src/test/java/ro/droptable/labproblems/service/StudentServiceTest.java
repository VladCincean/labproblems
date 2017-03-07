package ro.droptable.labproblems.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.InMemoryRepository;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/7/2017.
 */
public class StudentServiceTest {
    InMemoryRepository<Long, Student> r = new InMemoryRepository<Long, Student>(new StudentValidator());
    StudentService s = new StudentService(r);

    @Before
    public void setUp() throws Exception {
        s.add("1","alice", 222);
        s.add("1", "alice", 222);
        s.add("1", "bab", 222);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {
        s.add("3","alice", 222);
        assertTrue(s.getAll().size() == 3);
        try{
            s.add("3","alice", 200);
            assertTrue(false);
        }catch (ValidatorException e){

        }
    }

    @Test
    public void filterStudentsByName() throws Exception {
        assertTrue(s.filterStudentsByName("al").size() == 1);
        assertTrue(s.filterStudentsByName("a").size() == 2);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void getAll() throws Exception {
        assertTrue(s.getAll().size() == 2);
        s.add("3","alice", 222);
        assertTrue(s.getAll().size() == 3);
    }

}