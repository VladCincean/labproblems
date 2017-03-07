package ro.droptable.labproblems.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.InMemoryRepository;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/7/2017.
 */
public class ProblemServiceTest {
    ProblemService ps = new ProblemService(new InMemoryRepository<Long, Problem>(new ProblemValidator()));
    @Before
    public void setUp() throws Exception {
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
        try{
            ps.add("","a");
            assertTrue(false);
        }catch (ValidatorException e){

        }
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void getAll() throws Exception {
        assertTrue(ps.getAll().size() == 2);
        ps.add("3","alice");
        assertTrue(ps.getAll().size() == 3);
    }

}