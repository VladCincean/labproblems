package ro.droptable.labproblems.repository;

import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.validators.AssignmentValidator;

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
public class AssignmentXmlRepositoryTest {
    String fileName = "src/test/resources/assignments.xml";
    private final Path path = Paths.get(fileName);
    AssignmentXmlRepository ar;
    Assignment a1 = new Assignment(1L, 1L);
    Assignment a2 = new Assignment(2L, 2L);

    @Before
    public void setUp() throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            Files.write(path, "".getBytes());
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><assignments></assignments>");
        } catch (IOException e) {
            System.err.println("AssignmentFileRepository::setUp() failed: " + e.getMessage());
        }

        ar = new AssignmentXmlRepository(new AssignmentValidator(), path.toString());

        ar.save(a1);
    }

    @Test
    public void save() throws Exception {
        ar.save(a2);
        String line12 = Files.readAllLines(path).get(11);
        assertTrue(line12.equals("    <problemId>2</problemId>"));
    }

    @Test
    public void delete() throws Exception {
        ar.delete(a1.getId());
        String line4 = Files.readAllLines(path).get(3);
        assertTrue(line4.equals("</assignments>"));
    }

    @Test
    public void update() throws Exception {
        Assignment nota1 = new Assignment(a1.getStudentId()+1, a1.getProblemId());
        nota1.setId(a1.getId());
        ar.update(nota1);
        String line5 = Files.readAllLines(path).get(4);
        assertTrue(line5.equals("    <studentId>2</studentId>"));
    }

    @Test
    public void findOne() throws Exception {
        Assignment found = ar.findOne(a1.getId()).get();
        assertTrue(found.equals(a1));
        assertFalse(ar.findOne(a1.getId()+1L).isPresent());
    }

    @Test
    public void findAll() throws Exception {
        ar.save(a2);
        assertTrue(StreamSupport.stream(ar.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

}