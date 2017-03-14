package ro.droptable.labproblems.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.InMemoryRepository;
import ro.droptable.labproblems.service.ProblemService;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/7/2017.
 */
public class ProblemServiceTest {
    ProblemService ps;

    @Before
    public void setUp() throws Exception {
        ps = new ProblemService(new InMemoryRepository<>(new ProblemValidator()));

        ps.add("bla", "blabla");
        ps.add("w", "hy");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {
        ps.add("a","b");
        assertTrue(ps.getAll().size() == 3);
        try {
            ps.add("","a");
            assertTrue(false);
        } catch (ValidatorException e) {
            assertFalse(false);
        }
    }

    @Test
    public void delete() throws Exception {
        ps.delete(ps.getByAttributes("bla", "blabla").get().getId());
        assert(ps.getAll().size() == 1);
    }

    @Test
    public void findOne() throws Exception {
        assertTrue(ps.findOne(ps.getByAttributes("bla", "blabla").get().getId()).get().getDescription().equals("blabla"));
    }

    @Test
    public void getAll() throws Exception {
        assertTrue(ps.getAll().size() == 2);
        ps.add("3","alice");
        assertTrue(ps.getAll().size() == 3);
        assertTrue(ps.getAll().stream()
                .filter(e -> e.getDescription().equals("alice"))
                .collect(Collectors.toList())
                .size() == 1
        );
    }

    @Test
    public void update() throws Exception {
        long id = ps.getByAttributes("bla", "blabla").get().getId();
        ps.update(id, "b", "b");
        assertTrue(ps.findOne(id).get().getTitle().equals("b"));
        try {
            ps.update(id, "", "b");
            assertTrue(false);
        }catch(ValidatorException e){}
        assertTrue(ps.findOne(id).get().getTitle().equals("b"));
    }

}