package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Student;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Student}
 *      while maintaining file persistence
 */
public class StudentFileRepository extends InMemoryRepository<Long, Student> {
    private String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
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
                String serialNumber = items.get(1);
                String name = items.get(2);
                int group = Integer.parseInt(items.get(3));

                Student student = new Student(serialNumber, name, group);
                student.setId(id);

                try {
                    super.save(student);
                } catch (ValidatorException e) {
                    e.printStackTrace(); // TODO: do something else
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void saveToFile(Student entity) {
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

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        Optional<Student> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        optional.ifPresent(s -> {
           Long sId = s.getId();

           Path path = Paths.get(fileName);
           StringBuilder stringBuilder = new StringBuilder("");

           try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE)) {
               Files.lines(path, StandardCharsets.UTF_8)
                       .filter(line -> !line.startsWith(sId.toString() + ","))
                       .forEach(line -> stringBuilder.append(line + "\n"));
               bufferedWriter.write(stringBuilder.toString());
           } catch (IOException e) {
               e.printStackTrace(); // TODO: do something else
           }
        });

        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        Optional<Student> student = super.update(entity);

        throw new NotImplementedException();
    }
}
