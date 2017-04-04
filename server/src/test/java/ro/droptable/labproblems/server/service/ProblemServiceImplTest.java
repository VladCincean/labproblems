package ro.droptable.labproblems.server.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
import ro.droptable.labproblems.server.repository.ProblemDbRepository;
import ro.droptable.labproblems.server.repository.Repository;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/29/2017.

public class ProblemServiceImplTest {
    ProblemService ps;

    @Before
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "admin";

        ExecutorService executorService = Executors.newFixedThreadPool(
                1
        );

        Repository<Long, Problem> problemRepository = new ProblemDbRepository(
                new ProblemValidator(),
                url,
                username,
                password
        );

        ps = new ProblemServiceImpl(executorService, problemRepository);

        ps.deleteProblem("1");
        ps.deleteProblem("2");
        ps.deleteProblem("3");
        ps.addProblem("1,bla,blabla");
        ps.addProblem("2,w,hy");
    }

    @After
    public void tearDown() throws Exception {
        ps.deleteProblem("1");
        ps.deleteProblem("2");
        ps.deleteProblem("3");
    }

    @Test
    public void addProblem() throws Exception {
        ps.addProblem("3,a,b");
        assertTrue(Arrays.asList(ps.findAllProblems("").get().split("\n")).size() == 3);

        ps.addProblem("4,,a");
        assertTrue(Arrays.asList(ps.findAllProblems("").get().split("\n")).size() == 3);
    }

    @Test
    public void deleteProblem() throws Exception {
        ps.deleteProblem("1");
        assert(Arrays.asList(ps.findAllProblems("").get().split("\n")).size() == 1);
    }

    @Test
    public void findOneProblem() throws Exception {
        assertTrue(ps.findOneProblem("2").get().equals("2,w,hy"));
    }

    @Test
    public void getAllProblems() throws Exception {
        assertTrue(Arrays.asList(ps.findAllProblems("").get().split("\n")).size() == 2);
        ps.addProblem("3,eh,alice");
        assertTrue(Arrays.asList(ps.findAllProblems("").get().split("\n")).size() == 3);
    }

    @Test
    public void updateProblem() throws Exception {
        ps.updateProblem("1,b,b");
        assertTrue(ps.findOneProblem("1").get().equals("1,b,b"));
        ps.updateProblem("1,,b");
        assertTrue(ps.findOneProblem("1").get().equals("1,b,b"));
    }
}*/