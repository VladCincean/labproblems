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
        Student sb = new Student("1", "Bob", 222);
        s.add(sb);
        System.out.println(s.getAll());
        s.delete(st);
        s.delete(st);
        System.out.println(s.getAll());
        sb.setGroup(221);
        System.out.println(s.getAll());
        s.update(sb);
        System.out.println(s.getAll());
    }
}
