package ro.droptable.labproblems.repository;

import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.ProblemValidator;

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
public class ProblemXmlRepositoryTest {
    String fileName = "src/test/resources/problems.xml";
    private final Path path = Paths.get(fileName);
    ProblemXmlRepository pr;
    Problem p1 = new Problem("p1", "do something");
    Problem p2 = new Problem("p2", "do sth else");

    @Before
    public void setUp() throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            Files.write(path, "".getBytes());
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><problems></problems>");
        } catch (IOException e) {
            System.err.println("ProblemFileRepository::setUp() failed: " + e.getMessage());
        }

        pr = new ProblemXmlRepository(new ProblemValidator(), path.toString());

        pr.save(p1);
    }

    @Test
    public void save() throws Exception {
        pr.save(p2);
        String line14 = Files.readAllLines(path).get(10);
        assertTrue(line14.equals("    <description>do sth else</description>"));
    }

    @Test
    public void delete() throws Exception {
        pr.delete(p1.getId());
        String line4 = Files.readAllLines(path).get(3);
        assertTrue(line4.equals("</problems>"));
    }

    @Test
    public void update() throws Exception {
        Problem notp1 = new Problem("not p1", p1.getDescription());
        notp1.setId(p1.getId());
        pr.update(notp1);
        String line5 = Files.readAllLines(path).get(4);
        assertTrue(line5.equals("    <title>not p1</title>"));
    }

    @Test
    public void findOne() throws Exception {
        Problem found = pr.findOne(p1.getId()).get();
        assertTrue(found.equals(p1));
        assertFalse(pr.findOne(p1.getId()+1L).isPresent());
    }

    @Test
    public void findAll() throws Exception {
        pr.save(p2);
        assertTrue(StreamSupport.stream(pr.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

}