package ro.droptable.labproblems.server.repository;

import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.LabProblemsException;
import ro.droptable.labproblems.common.domain.validators.Validator;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Created by vlad on 07.03.2017.
 *
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Assignment}
 *      while maintaining database persistence
 */
@Deprecated
public class AssignmentDbRepository implements Repository<Long, Assignment> {
    private Validator<Assignment> validator;
    private String url;
    private String username;
    private String password;

    public AssignmentDbRepository(Validator<Assignment> validator,
                                  String url,
                                  String username,
                                  String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;

        StreamSupport.stream(findAll().spliterator(), false)
                .map(s -> s.getId())
                .max(Comparator.naturalOrder())
                .ifPresent(o -> {
                    Class assignmentClass;
                    try {
                        assignmentClass = Class.forName("ro.droptable.labproblems.domain.Assignment");
                        Assignment assignmentInstance = (Assignment) assignmentClass.newInstance();

                        Field currentIdField = assignmentClass.getDeclaredField("currentId");
                        currentIdField.setAccessible(true);
                        currentIdField.set(assignmentInstance, o + 1);
                        currentIdField.setAccessible(false);
                    } catch (ClassNotFoundException
                            | NoSuchFieldException
                            | IllegalAccessException
                            | InstantiationException e) {
                        //...
                    }
                });
    }

    @Override
    public Optional<Assignment> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM assignments WHERE id=?"))
        {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long assignmentId = resultSet.getLong("id");
                    Long studentId = resultSet.getLong("student_id");
                    Long problemId = resultSet.getLong("problem_id");
                    Double grade = resultSet.getDouble("grade");

                    Assignment assignment = new Assignment(assignmentId, studentId, problemId);
                    assignment.setGrade(grade);

                    return Optional.of(assignment);
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Assignment> findAll() {
        List<Assignment> assignments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM assignments"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long assignmentId = resultSet.getLong("id");
                    Long studentId = resultSet.getLong("student_id");
                    Long problemId = resultSet.getLong("problem_id");
                    Double grade = resultSet.getDouble("grade");

                    Assignment assignment = new Assignment(assignmentId, studentId, problemId);
                    assignment.setGrade(grade);

                    assignments.add(assignment);
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }

        return assignments;
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO assignments (id, student_id, problem_id, grade) VALUES (?,?,?,?)"
             ))
        {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getStudentId());
            statement.setLong(3, entity.getProblemId());
            statement.setDouble(4, entity.getGrade());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ... (log exception)
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Assignment> assignment = findOne(id); // .?
        if (!assignment.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM assignments WHERE id=?"))
        {
            statement.setLong(1, id);

            statement.executeUpdate();

            return assignment;
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE assignments SET student_id=?, problem_id=?, grade=? WHERE id=?"
             ))
        {
            statement.setLong(1, entity.getStudentId());
            statement.setLong(2, entity.getProblemId());
            statement.setDouble(3, entity.getGrade());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ... (log exception)
            return Optional.of(entity);
        }
    }
}
