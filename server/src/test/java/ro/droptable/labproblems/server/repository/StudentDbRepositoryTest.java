package ro.droptable.labproblems.server.repository;

import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/28/2017.
 */
public class StudentDbRepositoryTest {String url = "jdbc:postgresql://localhost:5432/test";
    String username = "postgres";
    String password = "admin";

    StudentDbRepository studentRepository;

    Student sa = new Student("1","alice", 222);
    Student sb = new Student("1", "alice", 222);
    Student sc = new Student("1", "bob", 222);
    Student sx = new Student("2", "michael", 922);

    @Before
    public void setUp() throws Exception {
        studentRepository = new StudentDbRepository(new StudentValidator(), url, username, password);
        Iterable<Student> crt = studentRepository.findAll();
        crt.forEach(s -> studentRepository.delete(s.getId()));
        studentRepository.save(sa);
        studentRepository.save(sb);
        studentRepository.save(sc);
    }
    @Test
    public void findOne() throws Exception {
        assertTrue(studentRepository.findOne(sb.getId()).get().equals(sb));
        assertNull(studentRepository.findOne(sx.getId()).orElse(null));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void save() throws Exception {
        Student sp = new Student("","", 10);
        try {
            studentRepository.save(sp);
            assertTrue(false);
        } catch (ValidatorException e) {
            assertTrue(e.getMessage().equals("student name is empty; student serial number is empty; student group is invalid"));
        }
    }

    @Test
    public void delete() throws Exception {
        studentRepository.delete(sa.getId());
        assertTrue(StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
        studentRepository.delete(sa.getId());
        assertTrue(StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void update() throws Exception {
        Student saa = new Student("1", "alice", 44);
        saa.setId(sa.getId());
        try {
            studentRepository.update(saa);
            assertFalse(true);
        } catch (ValidatorException e) {
            assertTrue(studentRepository.findOne(sa.getId()).get().getGroup() == 222);
        }
    }
}