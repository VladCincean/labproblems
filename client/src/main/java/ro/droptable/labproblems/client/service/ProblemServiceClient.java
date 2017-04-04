package ro.droptable.labproblems.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by stefana on 3/28/2017.
 */
public class ProblemServiceClient implements ProblemService {

    @Autowired
    private ProblemService problemService;

    @Override
    public void addProblem(String title, String description) throws ValidatorException {
        problemService.addProblem(title, description);
    }

    @Override
    public void deleteProblem(Long id) throws ValidatorException {
        problemService.deleteProblem(id);
    }

    @Override
    public void updateProblem(long id, String title, String description) throws NoSuchElementException, ValidatorException {
        problemService.updateProblem(id, title, description);
    }

    @Override
    public Optional<Problem> findOneProblem(Long id) {
        return problemService.findOneProblem(id);
    }

    @Override
    public Set<Problem> findAllProblems() {
        return problemService.findAllProblems();
    }

    @Override
    public Set<Problem> filterProblemsByTitle(String string) {
        return problemService.filterProblemsByTitle(string);
    }
}
