package ro.droptable.labproblems.repository;

import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/21/2017.
 */
public class ProblemDbRepositoryTest {
    String url = "jdbc:postgresql://localhost:5432/test";
    String username = "postgres";
    String password = "admin";
    Problem p1 = new Problem("bla", "blabla");
    Problem p2 = new Problem("w", "hy");

    ProblemDbRepository problemDbRepository;
    @Before
    public void setUp() throws Exception {
        problemDbRepository = new ProblemDbRepository(new ProblemValidator(), url, username, password);
        Iterable<Problem> crt = problemDbRepository.findAll();
        crt.forEach(s -> problemDbRepository.delete(s.getId()));
        problemDbRepository.save(p1);
        problemDbRepository.save(p2);
    }

    @Test
    public void findOne() throws Exception {
        assertTrue(problemDbRepository.findOne(p1.getId()).get().equals(p1));
    }

    @Test
    public void findAll() throws Exception {
        assertTrue(StreamSupport.stream(problemDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 2);
    }

    @Test
    public void save() throws Exception {
        problemDbRepository.save(new Problem("a","b"));
        assertTrue(StreamSupport.stream(problemDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 3);
        try {
            problemDbRepository.save(new Problem("","a"));
            assertTrue(false);
        } catch (ValidatorException e) {
            assertFalse(false);
        }
    }

    @Test
    public void delete() throws Exception {
        problemDbRepository.delete(p1.getId());
        assert(StreamSupport.stream(problemDbRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet())
                .size() == 1);
    }

    @Test
    public void update() throws Exception {
        long id = p1.getId();
        Problem p3 = new Problem("b", "b");
        p3.setId(id);
        problemDbRepository.update(p3);
        assertTrue(problemDbRepository.findOne(id).get().getTitle().equals("b"));
        try {
            p3 = new Problem("", "b");
            p3.setId(id);
            problemDbRepository.update(p3);
            assertTrue(false);
        }catch(ValidatorException e){}
        assertTrue(problemDbRepository.findOne(id).get().getTitle().equals("b"));
    }

}