package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

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
    }

    @Override
    public Optional<Student> findOne(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Student> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Student> delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        throw new NotImplementedException();
    }
}
