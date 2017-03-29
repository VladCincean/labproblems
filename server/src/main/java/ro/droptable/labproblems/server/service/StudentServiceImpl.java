package ro.droptable.labproblems.server.service;

import ro.droptable.labproblems.common.StudentService;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.Repository;
import ro.droptable.labproblems.server.repository.StudentDbRepository;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceImpl implements StudentService {
    private ExecutorService executorService;
    private StudentDbRepository studentRepository;

    public StudentServiceImpl(ExecutorService executorService, Repository<Long, Student> studentRepository) {
        this.executorService = executorService;
        this.studentRepository = (StudentDbRepository)studentRepository;
    }

    @Override
    public Future<String> addStudent(String string) throws ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            String serialNumber = fields.get(1);
            String name = fields.get(2);
            int group = Integer.parseInt(fields.get(3));

            Student student = new Student(id, serialNumber, name, group);

            Optional<Student> optional = studentRepository.save(student);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> deleteStudent(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Student> optional = studentRepository.delete(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> updateStudent(String string) throws NoSuchElementException, ValidatorException {
        return executorService.submit(() -> {
            List<String> fields = Arrays.asList(string.split(","));

            Long id = Long.valueOf(fields.get(0));
            String serialNumber = fields.get(1);
            String name = fields.get(2);
            int group = Integer.parseInt(fields.get(3));

            // throws NoSuchElementException if the old Student does not exist
            Student oldStudent = studentRepository.findOne(Long.valueOf(id)).get();
            Student newStudent = new Student(
                    id,
                    serialNumber.equals("") ? oldStudent.getSerialNumber() : serialNumber,
                    name.equals("") ? oldStudent.getName() : name,
                    group == 0 ? oldStudent.getGroup() : group
            );

            Optional<Student> optional = studentRepository.update(newStudent);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findOneStudent(String string) {
        return executorService.submit(() -> {
            Long id = Long.valueOf(string);

            Optional<Student> optional = studentRepository.findOne(id);

            return optional.isPresent() ? optional.get().toCsv() : "";
        });
    }

    @Override
    public Future<String> findAllStudents(String string) {
        return executorService.submit(() -> {
            Iterable<Student> allStudents = studentRepository.findAll();

            return StreamSupport.stream(allStudents.spliterator(), false)
                    .map(Student::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }

    @Override
    public Future<String> filterStudentsByName(String string) {
        return executorService.submit(() -> {
            Iterable<Student> allStudents = studentRepository.findAll();

            return StreamSupport.stream(allStudents.spliterator(), false)
                    .filter(s -> s.getName().contains(string))
                    .map(Student::toCsv)
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
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

    public Future<String> reportStudentAverage() {
        return executorService.submit(() -> {
            Map<Student, Double> studentDoubleMap = studentRepository.reportStudentAverage();
            return studentDoubleMap.entrySet().stream()
                    .map(e-> e.getKey().toCsv() + "," + e.getValue().toString())
                    .reduce("", (acc, it) -> acc + it + "\n");
        });
    }

}
