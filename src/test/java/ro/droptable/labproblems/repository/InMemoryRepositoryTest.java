package ro.droptable.labproblems.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/5/2017.
 */
public class InMemoryRepositoryTest {
    InMemoryRepository<Long, Student> s = new InMemoryRepository<Long, Student>(new StudentValidator());
    Student st = new Student("1","alice", 222);
    Student sb = new Student("1", "alice", 222);
    Student sc = new Student("1", "bob", 222);

    @Before
    public void setUp() throws Exception {
        s.save(st);
        s.save(sb);
        s.save(sc);
    }

    @After
    public void tearDown() throws Exception {
        s = new InMemoryRepository<Long, Student>(new StudentValidator());
    }

    @Test
    public void findOne() throws Exception {
        assertTrue(s.findOne(sb.getId()).get().equals(sb));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false).collect(Collectors.toSet()).size() == 2);
    }

    @Test
    public void save() throws Exception {
        Student sp = new Student("","", 10);
        try {
            s.save(sp);
            assertTrue(false);
        }catch (ValidatorException e)
        {
            assertTrue(e.getMessage().equals("\nstudent name is empty; student serial number is empty; student group is invalid; "));
        }
    }

    @Test
    public void delete() throws Exception {
        s.delete(st.getId());
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false).collect(Collectors.toSet()).size() == 2);
        s.delete(st.getId());
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false).collect(Collectors.toSet()).size() == 2);
    }

    @Test
    public void update() throws Exception {
        st.setGroup(40);
        try {
            s.update(st);
            assertTrue(false);
        }catch (ValidatorException e){}
    }

}