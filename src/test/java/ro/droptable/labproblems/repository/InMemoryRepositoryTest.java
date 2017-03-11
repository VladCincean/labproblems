package ro.droptable.labproblems.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.InMemoryRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/5/2017.
 */
public class InMemoryRepositoryTest {
    InMemoryRepository<Long, Student> s;
    Student sa = new Student("1","alice", 222);
    Student sb = new Student("1", "alice", 222);
    Student sc = new Student("1", "bob", 222);
    Student sx = new Student("2", "michael", 922);

    @Before
    public void setUp() throws Exception {
        s = new InMemoryRepository<>(new StudentValidator());
        s.save(sa);
        s.save(sb);
        s.save(sc);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOne() throws Exception {
        assertTrue(s.findOne(sb.getId()).get().equals(sb));
        assertNull(s.findOne(sx.getId()).orElse(null));

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void save() throws Exception {
        Student sp = new Student("","", 10);
        try {
            s.save(sp);
            assertTrue(false);
        } catch (ValidatorException e) {
            assertTrue(e.getMessage().equals("student name is empty; student serial number is empty; student group is invalid"));
        }
    }

    @Test
    public void delete() throws Exception {
        s.delete(sa.getId());
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
        s.delete(sa.getId());
        assertTrue(StreamSupport.stream(s.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void update() throws Exception {
        Student saa = new Student("1", "alice", 44);
        saa.setId(sa.getId());
        try {
            s.update(saa);
            assertFalse(true);
        } catch (ValidatorException e) {
            assertTrue(s.findOne(sa.getId()).get().getGroup() == 222);
        }
    }
}