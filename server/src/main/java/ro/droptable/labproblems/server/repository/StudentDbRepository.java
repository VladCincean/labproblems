package ro.droptable.labproblems.server.repository;

import ro.droptable.labproblems.common.domain.Assignment;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.LabProblemsException;
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

        StreamSupport.stream(findAll().spliterator(), false)
                .map(s -> s.getId())
                .max(Comparator.naturalOrder())
                .ifPresent(o -> {
                    Class studentClass;
                    try {
                        studentClass = Class.forName("ro.droptable.labproblems.domain.Student");
                        Student studentInstance = (Student) studentClass.newInstance();

                        Field currentIdField = studentClass.getDeclaredField("currentId");
                        currentIdField.setAccessible(true);
                        currentIdField.set(studentInstance, o + 1);
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

    @SuppressWarnings("Duplicates")
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
             PreparedStatement deleteStudentStatement = connection.prepareStatement(
                     "DELETE FROM students WHERE id=?"
             );
             PreparedStatement deleteAssignmentsStatement = connection.prepareStatement(
                     "DELETE FROM assignments where student_id=?"
             ))
        {
            deleteStudentStatement.setLong(1, id);
            deleteAssignmentsStatement.setLong(1, id);

            deleteAssignmentsStatement.executeUpdate();
            deleteStudentStatement.executeUpdate();

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
                     "UPDATE students SET serial_number=?, name=?, \"group\"=? WHERE id=?"
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

    private Iterable<Assignment> getAllAssignments(){
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
