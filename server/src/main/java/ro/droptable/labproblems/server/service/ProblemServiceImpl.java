package ro.droptable.labproblems.server.service;

import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/28/2017.
 */
@Deprecated
public class ProblemServiceImpl implements ProblemService {
    private ExecutorService executorService;
    private Repository<Long, Problem> problemRepository;

    public ProblemServiceImpl(ExecutorService executorService, Repository<Long, Problem> problemRepository) {
        this.executorService = executorService;
        this.problemRepository = problemRepository;
    }

    @Override
    public Future<String> addProblem(String string) throws ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            String title = fields.get(1);
            String description = fields.get(2);

            Problem problem = new Problem(id, title, description);

            Optional<Problem> optional = problemRepository.save(problem);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> deleteProblem(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Problem> optional = problemRepository.delete(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> updateProblem(String string) throws NoSuchElementException, ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            String title = fields.get(1);
            String description = fields.get(2);

            // throws NoSuchElementException if the old Problem does not exist
            Problem oldProblem = problemRepository.findOne(Long.valueOf(id)).get();
            Problem newProblem = new Problem(
                    id,
                    title.equals("") ? oldProblem.getTitle() : title,
                    description.equals("") ? oldProblem.getDescription() : description
            );

            Optional<Problem> optional = problemRepository.update(newProblem);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findOneProblem(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Problem> optional = problemRepository.findOne(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findAllProblems(String string) {
        return executorService.submit(() -> {
            Iterable<Problem> allProblems = problemRepository.findAll();

            return StreamSupport.stream(allProblems.spliterator(), false)
                    .map(Problem::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }

    @Override
    public Future<String> filterProblemsByTitle(String string) {
        return executorService.submit(() -> {
            Iterable<Problem> allProblems = problemRepository.findAll();

            return StreamSupport.stream(allProblems.spliterator(), false)
                    .filter(p -> p.getTitle().contains(string))
                    .map(Problem::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }
}
