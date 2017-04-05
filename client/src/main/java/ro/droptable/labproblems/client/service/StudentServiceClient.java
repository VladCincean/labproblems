package ro.droptable.labproblems.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.domain.Student;
import ro.droptable.labproblems.common.service.StudentService;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by vlad on 28.03.2017.
 */
public class StudentServiceClient implements StudentService {

    @Autowired
    private StudentService studentService;


    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudent(String serialNumber, String name, int group) throws ValidatorException {
        studentService.addStudent(serialNumber, name, group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStudent(Long id, String serialNumber, String name, int group) throws ValidatorException {
        studentService.updateStudent(id, serialNumber, name, group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Student> findOneStudent(Long id) {
        return studentService.findOneStudent(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Student> filterStudentsByName(String name) {
        return studentService.filterStudentsByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int filterLargestGroup(){ return studentService.filterLargestGroup(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Student, Double> reportStudentAverage(){ return  studentService.reportStudentAverage(); }
}
