package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Assignment;
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
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining 'text file' persistence
 */
public class AssignmentFileRepository extends FileRepository<Long, Assignment>  {

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
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
                long studentId = Long.parseLong(items.get(1));
                long problemId = Long.parseLong(items.get(2));
                double grade = Double.parseDouble(items.get(3));

                Assignment assignment = new Assignment(studentId, problemId);
                assignment.setGrade(grade);
                assignment.setId(id);
                try {
                    super.saveInMemory(assignment);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    @Override
    protected void saveToFile(Assignment entity) {
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
}
