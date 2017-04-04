package ro.droptable.labproblems.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.LabProblemsException;
import ro.droptable.labproblems.common.domain.validators.StudentValidator;
import ro.droptable.labproblems.common.domain.validators.Validator;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Created by vlad on 07.03.2017.
 *
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Student}
 *      while maintaining database persistence
 */
public class StudentDbRepository implements Repository<Long, Student> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Validator<Student> validator = new StudentValidator();

    @Override
    public Optional<Student> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = jdbcTemplate.queryForObject(
                sql, BeanPropertyRowMapper.newInstance(Student.class), id
        );

        return Optional.ofNullable(student);
    }

    @Override
    public Iterable<Student> findAll() {
        String sql = "SELECT * FROM students";
        List<Student> students = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(Student.class)
        );
        return students;
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "INSERT INTO students (id, serial_number, name, \"group\") VALUES (?,?,?,?)";
        int rowCount = jdbcTemplate.update(
                sql, entity.getId(), entity.getSerialNumber(), entity.getName(), entity.getGroup()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Student> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Student> studentOptional = findOne(id); // .?
        if (!studentOptional.isPresent()) {
            return Optional.empty();
        }

        String sqlStudents = "DELETE FROM students WHERE id = ?";
        String sqlAssignments = "DELETE FROM assignments where student_id = ?";

        int rowCount = jdbcTemplate.update(sqlStudents, id);
        jdbcTemplate.update(sqlAssignments, id);

        return rowCount == 0 ? Optional.empty() : studentOptional;
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);

        String sql = "UPDATE students SET serial_number = ?, name = ?, \"group\" = ? WHERE id = ?";
        int rowCount = jdbcTemplate.update(
                sql, entity.getSerialNumber(), entity.getName(), entity.getGroup(), entity.getId()
        );

        return rowCount == 0 ? Optional.of(entity) : Optional.empty();
    }

//    private Iterable<Assignment> getAllAssignments(){
//        List<Assignment> assignments = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM assignments"))
//        {
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    Long assignmentId = resultSet.getLong("id");
//                    Long studentId = resultSet.getLong("student_id");
//                    Long problemId = resultSet.getLong("problem_id");
//                    Double grade = resultSet.getDouble("grade");
//
//                    Assignment assignment = new Assignment(assignmentId, studentId, problemId);
//                    assignment.setGrade(grade);
//
//                    assignments.add(assignment);
//                }
//            }
//        } catch (SQLException e) {
//            throw new LabProblemsException(e);
//        }
//
//        return assignments;
//    }
//
//    public Map<Student, Double> reportStudentAverage(){
//        HashMap<Student, Double> mp = new HashMap<>();
//        HashMap<Student, Double> nop = new HashMap<>();
//        Iterable<Student> students = findAll();
//        students.forEach(s -> {mp.put(s, 0.0); nop.put(s, 0.0);});
//        Iterable<Assignment> assignments = getAllAssignments();
//        System.out.println(assignments);
//        assignments.forEach(assignment -> {
//            System.out.println(assignment.toString() + " "+  mp.get(findOne(assignment.getStudentId()).get()) + " " + assignment.getGrade());
//            System.out.println(assignment.toString() + " "+  nop.get(findOne(assignment.getStudentId()).get()));
//            mp.put(findOne(assignment.getStudentId()).get(), mp.get(findOne(assignment.getStudentId()).get()) + assignment.getGrade());
//            nop.put(findOne(assignment.getStudentId()).get(), nop.get(findOne(assignment.getStudentId()).get()) + 1.0);
//        });
//
//        students.forEach(s -> mp.put(s, mp.get(s)/nop.get(s)));
//        return mp;
//    }
}
