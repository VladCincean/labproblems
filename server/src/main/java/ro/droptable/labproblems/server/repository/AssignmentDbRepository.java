package ro.droptable.labproblems.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.validators.AssignmentValidator;
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

public class AssignmentDbRepository implements Repository<Long, Assignment> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    AssignmentValidator validator = new AssignmentValidator();

    @Override
    public Optional<Assignment> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String sql = "SELECT * FROM assignments WHERE id = ?";
        Assignment assignment = jdbcTemplate.queryForObject(
                sql, BeanPropertyRowMapper.newInstance(Assignment.class), id
        );

        return Optional.ofNullable(assignment);
    }

    @Override
    public Iterable<Assignment> findAll() {
        String sql = "SELECT * FROM assignments";
        List<Assignment> assignments = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(Assignment.class)
        );
        return assignments;
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "INSERT INTO assignments (id, student_id, problem_id, grade) VALUES (?,?,?,?)";
        int rowCount = jdbcTemplate.update(
                sql, entity.getId(), entity.getStudentId(), entity.getProblemId(), entity.getGrade()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Assignment> assignmentOptional = findOne(id); // .?
        if (!assignmentOptional.isPresent()) {
            return Optional.empty();
        }

        String sqlAssignments = "DELETE FROM assignments where id = ?";

        int rowCount = jdbcTemplate.update(sqlAssignments, id);

        return rowCount == 0 ? Optional.empty() : assignmentOptional;
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "UPDATE assignments SET student_id = ?, problem_id = ?, grade = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(
                sql, entity.getStudentId(), entity.getProblemId(), entity.getGrade(), entity.getId()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }
}
