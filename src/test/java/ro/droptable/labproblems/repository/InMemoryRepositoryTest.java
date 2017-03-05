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

    }

    @Test
    public void findOne() throws Exception {
        assertTrue(s.findOne((long)1).get().equals(sb));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false).collect(Collectors.toSet()).size() == 2);
    }

    @Test
    public void save() throws Exception {
        Student sp = new Student(null,null, 10);
        try {
            s.save(sp);
            assertTrue(false);
        }catch (ValidatorException e)
        {
            assertTrue(e.getMessage().equals("; student name is null; student serial number is null; student group is invalid"));
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