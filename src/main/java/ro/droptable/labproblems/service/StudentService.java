package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.Repository;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 04.03.2017.
 */
public class StudentService extends Service<Student> {
    public StudentService(Repository<Long, Student > repository) {
        this.repository = repository;
    }

    /**
     * Saves the given entity.
     *
     * @param serialNumber
     *            must not be null.
     * @param name
     *            must not be null.
     * @param group
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void add(String serialNumber, String name, int group) throws ValidatorException {
        Class studentClass;

        try {
            studentClass = Class.forName("ro.droptable.labproblems.domain.Student");
            Student studentInstance = (Student)studentClass.newInstance();

            Field idField = studentClass.getSuperclass().getDeclaredField("id");
            Field currentIdField = studentClass.getDeclaredField("currentId");
            idField.setAccessible(true);
            currentIdField.setAccessible(true);
            idField.set(studentInstance, currentIdField.getLong(studentInstance));
            currentIdField.set(studentInstance, currentIdField.getLong(studentInstance) + 1);
            idField.setAccessible(false);
            currentIdField.setAccessible(false);

            Field serialNumberField = studentClass.getDeclaredField("serialNumber");
            serialNumberField.setAccessible(true);
            serialNumberField.set(studentInstance, serialNumber);
            serialNumberField.setAccessible(false);

            Field nameField = studentClass.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(studentInstance, name);
            nameField.setAccessible(false);

            Field groupField = studentClass.getDeclaredField("group");
            groupField.setAccessible(true);
            groupField.set(studentInstance, group);
            groupField.setAccessible(false);

            repository.save(studentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }
    }

    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.findAll();
        Set<Student> filteredStudents= new HashSet<>();
        students.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(s));
        return filteredStudents;
    }

    public Optional<Student> getByAttributes(String serialNumber, String name, int group)
    {
        Student s = new Student(serialNumber, name, group);
        return super.getByAttributes(s);
    }
    public void update(long id, String serialNumber, String name, int group) throws NoSuchElementException, ValidatorException
    {
        Student oldStudent = findOne(id).get(); //throws NoSuchElementException if the old student does not exist
        Class studentClass;

        try {
            studentClass = Class.forName("ro.droptable.labproblems.domain.Student");
            Student studentInstance = (Student) studentClass.newInstance();

            //create a new instance with the same id - do not modify current id
            Field idField = studentClass.getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(studentInstance, id);
            idField.setAccessible(false);

            Field serialNumberField = studentClass.getDeclaredField("serialNumber");
            serialNumberField.setAccessible(true);
            serialNumberField.set(studentInstance, serialNumber);
            serialNumberField.setAccessible(false);

            Field nameField = studentClass.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(studentInstance, name);
            nameField.setAccessible(false);

            Field groupField = studentClass.getDeclaredField("group");
            groupField.setAccessible(true);
            groupField.set(studentInstance, group);
            groupField.setAccessible(false);

            this.repository.update(studentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }

    }

}
