package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.repository.Repository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 04.03.2017.
 */
public class StudentService extends Service {
    public StudentService(Repository<Long, BaseEntity<Long> > repository) {
        this.repository = repository;
    }
/*  public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.findAll();
        Set<Student> filteredStudents = StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getName().contains(s)).collect(Collectors.toSet());
        return filteredStudents;
    }*/
}
