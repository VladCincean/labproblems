package ro.droptable.labproblems.repository;

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

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/21/2017.
 */
public class StudentXmlRepositoryTest {
    String fileName = "src/test/resources/students.xml";
    private final Path path = Paths.get(fileName);
    StudentXmlRepository sr;
    @Before
    public void setUp() throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            Files.write(path, "".getBytes());
            bufferedWriter.write(" ");
        } catch (IOException e) {
            System.err.println("StudentFileRepository::setUp() failed: " + e.getMessage());
        }

        sr = new StudentXmlRepository(new StudentValidator(), path.toString());
        Student s = new Student("1", "alice", 111);
        sr.save(s);
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

}