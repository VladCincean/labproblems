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

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        StreamSupport.stream(findAll().spliterator(), false)
                .map(Student::getId)
                .max(Comparator.naturalOrder())
                .ifPresent(o -> {
                    Class studentClass;
                    try {
                        studentClass = Class.forName("ro.droptable.labproblems.common.domain.Student");
                        Student studentInstance = (Student) studentClass.newInstance();

                        Field currentIdField = studentClass.getDeclaredField("currentId");
                        currentIdField.setAccessible(true);
                        currentIdField.set(studentInstance, o + 1);
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

        jdbcTemplate.update(sqlAssignments, id);
        int rowCount = jdbcTemplate.update(sqlStudents, id);

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

    private Iterable<Assignment> getAllAssignments(){
        String sql = "SELECT * FROM assignments";
        List<Assignment> assignments = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(Assignment.class)
        );
        return assignments;
    }

    public Map<Student, Double> reportStudentAverage(){
        HashMap<Student, Double> mp = new HashMap<>();
        HashMap<Student, Double> nop = new HashMap<>();
        Iterable<Student> students = findAll();
        students.forEach(s -> {mp.put(s, 0.0); nop.put(s, 0.0);});
        Iterable<Assignment> assignments = getAllAssignments();
        System.out.println(assignments);
        assignments.forEach(assignment -> {
            System.out.println(assignment.toString() + " "+  mp.get(findOne(assignment.getStudentId()).get()) + " " + assignment.getGrade());
            System.out.println(assignment.toString() + " "+  nop.get(findOne(assignment.getStudentId()).get()));
            mp.put(findOne(assignment.getStudentId()).get(), mp.get(findOne(assignment.getStudentId()).get()) + assignment.getGrade());
            nop.put(findOne(assignment.getStudentId()).get(), nop.get(findOne(assignment.getStudentId()).get()) + 1.0);
        });

        students.forEach(s -> mp.put(s, mp.get(s)/nop.get(s)));
        return mp;
    }
}
