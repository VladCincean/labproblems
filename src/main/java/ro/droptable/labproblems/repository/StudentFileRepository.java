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
import java.util.Optional;

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

        optional.orElseGet(() -> {
            this.saveToFile(entity);
            return null;
        });
        return optional.isPresent() ? optional : Optional.empty();

//        if (optional.isPresent()) {
//            return optional;
//        }
//        saveToFile(entity);
//        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        optional.ifPresent(s -> {
            Path path = Paths.get(fileName);
            try {
                Files.write(path, "".getBytes());
            } catch (IOException e) {
                e.printStackTrace(); //...
            }
            super.findAll().forEach(this::saveToFile);
        });
        return optional.isPresent() ? optional : Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        Optional<Student> optional = super.update(entity);
        optional.ifPresent(s -> {
            try {
                Files.write(Paths.get(fileName), "".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.findAll().forEach(this::saveToFile);
        });
        return optional.isPresent() ? optional : Optional.empty();
    }
}
