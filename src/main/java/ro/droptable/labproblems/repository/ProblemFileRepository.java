package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Problem}
 *      while maintaining 'text file' persistence
 */
public class ProblemFileRepository extends InMemoryRepository<Long, Problem>  {
    private String fileName;

    public ProblemFileRepository(Validator<Problem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path, StandardCharsets.UTF_8).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String title = items.get(1);
                String description = items.get(2);

                Problem problem = new Problem(title, description);
                problem.setId(id);

                try {
                    super.save(problem);
                } catch (ValidatorException e) {
                    e.printStackTrace(); // TODO: do something else
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void saveToFile(Problem entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(entity.getId() + "," +
                    entity.getTitle() + "," +
                    entity.getDescription()
            );
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Problem> delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Problem> update(Problem entity) {
        throw new NotImplementedException();
    }
}
