package ro.droptable.labproblems.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.StudentValidator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by vlad on 12.03.2017.
 */
public class StudentFileRepositoryTest {
    private StudentFileRepository repository;
    private final String fileName = "src/test/resources/students.txt";
    private final Path path = Paths.get(fileName);
    private final Student alice = new Student("1740", "alice", 222);
    private final Student bob = new Student("1765", "bob", 222);

    @Before
    public void setUp() throws Exception {
//        Files.deleteIfExists(path);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            Files.write(path, "".getBytes());
            bufferedWriter.write("1,1740,alice,222\n2,1765,bob,222\n");
        } catch (IOException e) {
            System.err.println("StudentFileRepository::setUp() failed: " + e.getMessage());
        }

        repository = new StudentFileRepository(new StudentValidator(), fileName);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void save() throws Exception {
        Student student = new Student("777", "john", 223);
        student.setId(3L);
        Student student1 = new Student("888", "bill", 223);
        student1.setId(2L);
        assertNull(repository.save(student).orElse(null));
        assertTrue(repository.save(student1).get().equals(bob));
        //System.out.println(Files.lines(path).count());
        assertTrue(Files.lines(path).count() == 3);
        assertTrue(Files
                .lines(path)
                .reduce("", (acc, it) -> acc + it + "\n")
                .equals("1,1740,alice,222\n" +
                        "2,1765,bob,222\n" +
                        "3,777,john,223\n")
        );
    }

    @Test
    public void delete() throws Exception {
        assertNull(repository.delete(42L).orElse(null));
        assertTrue(repository.delete(1L).get().equals(alice));
        assertTrue(Files
                .lines(path)
                .reduce("", (acc, it) -> acc + it + "\n")
                .equals("2,1765,bob,222\n")
        );
    }

    @Test
    public void update() throws Exception {
        Student alice2 = new Student("111", "alice", 222);
        alice2.setId(1L);
        assertTrue(repository.update(alice2).get().equals(alice2));
        assertTrue(repository.findOne(1L).get().equals(alice2));
        assertFalse(repository.findOne(1L).get().equals(alice));
        assertTrue(Files
                .lines(path)
                .reduce("", (acc, it) -> acc + it + "\n")
                .equals("2,1765,bob,222\n1,111,alice,222\n")
        );
    }

    @Test
    public void findOne() throws Exception {
        assertTrue(repository.findOne(1L).get().equals(alice));
        assertTrue(repository.findOne(2L).get().equals(bob));
        assertNull(repository.findOne(3L).orElse(null));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }
}