package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.repository.Repository;

import java.util.HashSet;
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
    public Set<Student> filterStudentsByName(String s) {
        Set<Student> filteredStudents= new HashSet<>();
        filteredStudents.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(s));
        return filteredStudents;
    }
}
