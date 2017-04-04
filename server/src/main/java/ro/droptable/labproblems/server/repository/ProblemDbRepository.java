package ro.droptable.labproblems.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.LabProblemsException;
import ro.droptable.labproblems.common.domain.validators.ProblemValidator;
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
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Problem}
 *      while maintaining database persistence
 */

public class ProblemDbRepository implements Repository<Long, Problem> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Validator<Problem> validator = new ProblemValidator();

    @Override
    public Optional<Problem> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String sql = "SELECT * FROM problems WHERE id = ?";
        Problem problem = jdbcTemplate.queryForObject(
                sql, BeanPropertyRowMapper.newInstance(Problem.class), id
        );

        return Optional.ofNullable(problem);
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

        int rowCount = jdbcTemplate.update(sqlProblems, id);
        jdbcTemplate.update(sqlAssignments, id);

        return rowCount == 0 ? Optional.empty() : problemOptional;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "UPDATE problems SET title = ?, desciption = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(
                sql, entity.getTitle(), entity.getDescription(), entity.getId()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }
}
