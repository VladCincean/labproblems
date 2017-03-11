package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Assignment;
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
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining 'text file' persistence
 */
public class AssignmentFileRepository extends InMemoryRepository<Long, Assignment>  {
    private String fileName;

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
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
                long studentId = Long.parseLong(items.get(1));
                long problemId = Long.parseLong(items.get(2));
                double grade = Double.parseDouble(items.get(3));
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void saveToFile(Assignment entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(entity.getId() + "," +
                    entity.getStudentId() + "," +
                    entity.getProblemId() + "," +
                    entity.getGrade()
            );
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Assignment> update(Assignment entity) {
        throw new NotImplementedException();
    }
}
