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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/21/2017.
 */
public class StudentXmlRepositoryTest {
    String fileName = "src/test/resources/students.xml";
    private final Path path = Paths.get(fileName);
    StudentXmlRepository sr;
    Student alice = new Student("1", "alice", 111);
    Student bob = new Student("1765", "bob", 222);

    @Before
    public void setUp() throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            Files.write(path, "".getBytes());
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><students></students>");
        } catch (IOException e) {
            System.err.println("StudentFileRepository::setUp() failed: " + e.getMessage());
        }

        sr = new StudentXmlRepository(new StudentValidator(), path.toString());

        sr.save(alice);
    }

    @Test
    public void save() throws Exception {
        sr.save(bob);
        String line12 = Files.readAllLines(path).get(11);
        assertTrue(line12.equals("    <name>bob</name>"));
        sr.save(bob);
        String line15 = Files.readAllLines(path).get(14);
        assertTrue(line15.equals("</students>"));
    }

    @Test
    public void delete() throws Exception {
        sr.delete(alice.getId());
        String line4 = Files.readAllLines(path).get(3);
        assertTrue(line4.equals("</students>"));
    }

    @Test
    public void update() throws Exception {
        Student notAlice = new Student(alice.getSerialNumber(), "notalice", alice.getGroup());
        notAlice.setId(alice.getId());
        sr.update(notAlice);
        String line6 = Files.readAllLines(path).get(5);
        assertTrue(line6.equals("    <name>notalice</name>"));
    }

    @Test
    public void findOne() throws Exception {
        Student found = sr.findOne(alice.getId()).get();
        assertTrue(found.equals(alice));
        assertFalse(sr.findOne(alice.getId()+1L).isPresent());
    }

    @Test
    public void findAll() throws Exception {
        sr.save(bob);
        assertTrue(StreamSupport.stream(sr.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

}