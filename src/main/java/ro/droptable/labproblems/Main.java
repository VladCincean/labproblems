package ro.droptable.labproblems;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.repository.InMemoryRepository;
import ro.droptable.labproblems.repository.Repository;
import ro.droptable.labproblems.service.StudentService;

/**
 * Created by vlad on 04.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        Repository<Long, Student> r = new InMemoryRepository<Long, Student>(new StudentValidator());
        StudentService s = new StudentService(r);
        Student st = new Student("1","alice", 222);
        s.add(st);
        System.out.println(s.getAll());
    }
}
