package ro.droptable.labproblems.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.LabProblemsException;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
import ro.droptable.labproblems.common.domain.validators.Validator;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import javax.annotation.PostConstruct;
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
    @Autowired
    JdbcTemplate jdbcTemplate;

    Validator<Problem> validator = new ProblemValidator();

    @PostConstruct
    public void init() {
        StreamSupport.stream(findAll().spliterator(), false)
                .map(Problem::getId)
                .max(Comparator.naturalOrder())
                .ifPresent(o -> {
                    Class problemClass;
                    try {
                        problemClass = Class.forName("ro.droptable.labproblems.common.domain.Problem");
                        Problem problemInstance = (Problem) problemClass.newInstance();

                        Field currentIdField = problemClass.getDeclaredField("currentId");
                        currentIdField.setAccessible(true);
                        currentIdField.set(problemInstance, o + 1);
                        currentIdField.setAccessible(false);
                    } catch (ClassNotFoundException
                            | NoSuchFieldException
                            | IllegalAccessException
                            | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public Optional<Problem> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Iterable<Problem> problems = findAll();
        return StreamSupport.stream(problems.spliterator(), false).filter(s->s.getId().equals(id)).findAny();
    }

    @Override
    public Iterable<Problem> findAll() {
        String sql = "SELECT * FROM problems";
        List<Problem> problems = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(Problem.class)
        );
        return problems;
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "INSERT INTO problems (id, title, description) VALUES (?,?,?)";
        int rowCount = jdbcTemplate.update(
                sql, entity.getId(), entity.getTitle(), entity.getDescription()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public Optional<Problem> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Problem> problemOptional = findOne(id); // .?
        if (!problemOptional.isPresent()) {
            return Optional.empty();
        }

        String sqlProblems = "DELETE FROM problems WHERE id = ?";
        String sqlAssignments = "DELETE FROM assignments where problem_id = ?";

        jdbcTemplate.update(sqlAssignments, id);
        int rowCount = jdbcTemplate.update(sqlProblems, id);

        return rowCount == 0 ? Optional.empty() : problemOptional;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);
        Optional<Problem> assignmentOptional = findOne(entity.getId()); // .?
        if (!assignmentOptional.isPresent()) {
            return Optional.empty();
        }
        String sql = "UPDATE problems SET title = ?, description = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(
                sql, entity.getTitle(), entity.getDescription(), entity.getId()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }
}
