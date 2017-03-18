package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code FileRepository} for CRUD operations on a repository for type {@code Student}
 *      while maintaining file persistence
 */
public class StudentFileRepository extends FileRepository<Long, Student> {

    public StudentFileRepository(Validator<Student> validator, String fileName) {
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
                String serialNumber = items.get(1);
                String name = items.get(2);
                int group = Integer.parseInt(items.get(3));

                Student student = new Student(serialNumber, name, group);
                student.setId(id);

                try {
                    super.saveInMemory(student);
                } catch (ValidatorException e) {
                    e.printStackTrace(); // TODO: do something else
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    @Override
    protected void saveToFile(Student entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(entity.getId() + "," +
                            entity.getSerialNumber() + "," +
                            entity.getName() + "," +
                            entity.getGroup()
            );
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }
}
