package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.LabProblemsException;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Student}
 *      while maintaining database persistence
 */
public class StudentDbRepository implements Repository<Long, Student> {
    private Validator<Student> validator;
    private String url;
    private String username;
    private String password;

    public StudentDbRepository(Validator<Student> validator,
                               String url,
                               String username,
                               String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Student> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id=?"))
        {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long studentId = resultSet.getLong("id");
                    String serialNumber = resultSet.getString("serial_number");
                    String name = resultSet.getString("name");
                    int group = resultSet.getInt("group");

                    return Optional.of(new Student(studentId, serialNumber, name, group));
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long studentId = resultSet.getLong("id");
                    String serialNumber = resultSet.getString("serial_number");
                    String name = resultSet.getString("name");
                    int group = resultSet.getInt("group");

                    students.add(new Student(studentId, serialNumber, name, group));
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }

        return students;
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO students (id, serial_number, name, \"group\") VALUES (?,?,?,?)"
             ))
        {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getSerialNumber());
            statement.setString(3, entity.getName());
            statement.setInt(4, entity.getGroup());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ... (log exception)
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Student> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Student> student = findOne(id); // .?
        if (!student.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id=?"))
        {
            statement.setLong(1, id);

            statement.executeUpdate();

            return student;
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE students SET serial_number=?, name=?, group=? WHERE id=?"
             ))
        {
            statement.setString(1, entity.getSerialNumber());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getGroup());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ... (log exception)
            return Optional.of(entity);
        }
    }
}
