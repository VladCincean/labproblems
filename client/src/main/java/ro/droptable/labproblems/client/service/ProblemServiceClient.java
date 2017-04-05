package ro.droptable.labproblems.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by stefana on 3/28/2017.
 */
public class ProblemServiceClient implements ProblemService {

    @Autowired
    private ProblemService problemService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProblem(String title, String description) throws ValidatorException {
        problemService.addProblem(title, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProblem(Long id) {
        problemService.deleteProblem(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProblem(Long id, String title, String description) throws NoSuchElementException, ValidatorException {
        problemService.updateProblem(id, title, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Problem> findOneProblem(Long id) {
        return problemService.findOneProblem(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Problem> findAllProblems() {
        return problemService.findAllProblems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Problem> filterProblemsByTitle(String title) {
        return problemService.filterProblemsByTitle(title);
    }
}
