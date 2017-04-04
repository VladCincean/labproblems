package ro.droptable.labproblems.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceImpl implements StudentService {
    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    @Autowired
    private Repository<Long, Student> studentRepository;

//    @Deprecated
//    public StudentServiceImpl(ExecutorService executorService, Repository<Long, Student> studentRepository) {
//        this.executorService = executorService;
//        this.studentRepository = (StudentDbRepository)studentRepository;
//    }

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
    @Override
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
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

            studentRepository.save(studentInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }
    }

    @Override
    public void deleteStudent(Long id) {
        throw new NotImplementedException();

//        return executorService.submit(() -> {
//            Long id = Long.valueOf(string);
//
//            Optional<Student> optional = studentRepository.delete(id);
//
//            return optional.isPresent() ? optional.get().toCsv() : "";
//        });
    }

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

    @Override
    public Optional<Student> findOneStudent(Long id) {
        throw new NotImplementedException();

//        return executorService.submit(() -> {
//            Long id = Long.valueOf(string);
//
//            Optional<Student> optional = studentRepository.findOne(id);
//
//            return optional.isPresent() ? optional.get().toCsv() : "";
//        });
    }

    @Override
    public Set<Student> findAllStudents() {
        throw new NotImplementedException();

//        return executorService.submit(() -> {
//            Iterable<Student> allStudents = studentRepository.findAll();
//
//            return StreamSupport.stream(allStudents.spliterator(), false)
//                    .map(Student::toCsv)
//                    .reduce("", (acc, it) -> acc + it + "\n");
//        });
    }

    @Override
    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = studentRepository.findAll();
        Set<Student> filteredStudents= new HashSet<>();
        students.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(s));
        return filteredStudents;
    }

    private int getGroupSize(int group){
        Iterable<Student> students = studentRepository.findAll();
        return (int)StreamSupport.stream(students.spliterator(), false).filter(s->s.getGroup() == group).count();
    }

    public Future<String> filterLargestGroup(){
        return executorService.submit(() -> {
            Iterable<Student> students = studentRepository.findAll();
            Optional<Integer> mxGroup = StreamSupport.stream(students.spliterator(), false).
                    map(s -> getGroupSize(s.getGroup())).max(Comparator.naturalOrder());
            Integer res = StreamSupport.stream(students.spliterator(), false).
                    filter(s -> getGroupSize(s.getGroup()) == mxGroup.get()).findFirst().get().getGroup();
            return res.toString();
        });
    }

//    public Future<String> reportStudentAverage() {
//        return executorService.submit(() -> {
//            Map<Student, Double> studentDoubleMap = studentRepository.reportStudentAverage();
//            return studentDoubleMap.entrySet().stream()
//                    .map(e-> e.getKey().toCsv() + "," + e.getValue().toString())
//                    .reduce("", (acc, it) -> acc + it + "\n");
//        });
//    }

}
