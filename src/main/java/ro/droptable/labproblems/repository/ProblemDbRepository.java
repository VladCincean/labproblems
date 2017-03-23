package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.LabProblemsException;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

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
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Problem}
 *      while maintaining database persistence
 */
public class ProblemDbRepository implements Repository<Long, Problem> {
    private Validator<Problem> validator;
    private String url;
    private String username;
    private String password;

    public ProblemDbRepository(Validator<Problem> validator,
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
                    Class problemClass;
                    try {
                        problemClass = Class.forName("ro.droptable.labproblems.domain.Problem");
                        Problem problemInstance = (Problem) problemClass.newInstance();

                        Field currentIdField = problemClass.getDeclaredField("currentId");
                        currentIdField.setAccessible(true);
                        currentIdField.set(problemInstance, o + 1);
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
    public Optional<Problem> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM problems WHERE id=?"))
        {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long problemId = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    return Optional.of(new Problem(problemId, title, description));
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Problem> findAll() {
        List<Problem> problems = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM problems"))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long problemId = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    problems.add(new Problem(problemId, title, description));
                }
            }
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }

        return problems;
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO problems (id, title, description) VALUES (?,?,?)"
             ))
        {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getDescription());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ...
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Problem> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Problem> problem = findOne(id); // .?
        if (!problem.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM problems WHERE id=?"))
        {
            statement.setLong(1, id);

            statement.executeUpdate();

            return problem;
        } catch (SQLException e) {
            throw new LabProblemsException(e);
        }
    }

    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE problems SET title=?, description=? WHERE id=?"
             ))
        {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: ...
            return Optional.of(entity);
        }
    }
}
