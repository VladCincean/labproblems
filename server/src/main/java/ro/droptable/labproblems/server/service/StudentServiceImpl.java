package ro.droptable.labproblems.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.StudentDbRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDbRepository studentRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
        Class studentClass;

        try {
            studentClass = Class.forName("ro.droptable.labproblems.common.domain.Student");
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

            studentRepository.save(studentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStudent(Long id, String serialNumber, String name, int group) throws NoSuchElementException, ValidatorException
    {
        Student oldStudent = findOneStudent(id).get(); //throws NoSuchElementException if the old student does not exist
        Class studentClass;

        try {
            studentClass = Class.forName("ro.droptable.labproblems.common.domain.Student");
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

            studentRepository.update(studentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Student> findOneStudent(Long id) {
        return studentRepository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Student> findAllStudents() {
        Iterable<Student> entities = studentRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Student> filterStudentsByName(String name) {
        Iterable<Student> students = studentRepository.findAll();
        Set<Student> filteredStudents= new HashSet<>();
        students.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(name));
        return filteredStudents;
    }

    private int getGroupSize(int group){
        Iterable<Student> students = studentRepository.findAll();
        return (int)StreamSupport.stream(students.spliterator(), false).filter(s->s.getGroup() == group).count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int filterLargestGroup(){
        Iterable<Student> students = studentRepository.findAll();
        Optional<Integer> mxGroup = StreamSupport.stream(students.spliterator(), false).
                map(s -> getGroupSize(s.getGroup())).max(Comparator.naturalOrder());
        return StreamSupport.stream(students.spliterator(), false).
                filter(s -> getGroupSize(s.getGroup()) == mxGroup.get()).findFirst().get().getGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Student, Double> reportStudentAverage() {
        return studentRepository.reportStudentAverage();
    }

}
