package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Problem}
 *      while maintaining 'text file' persistence
 */
public class ProblemFileRepository extends FileRepository<Long, Problem>  {

    public ProblemFileRepository(Validator<Problem> validator, String fileName) {
        super(validator, fileName);

        loadData();
    }

    @Override
    protected void loadData() {
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
                    super.saveInMemory(problem);
                } catch (ValidatorException e) {
                    e.printStackTrace(); // TODO: do something else
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    @Override
    protected void saveToFile(Problem entity) {
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
}
