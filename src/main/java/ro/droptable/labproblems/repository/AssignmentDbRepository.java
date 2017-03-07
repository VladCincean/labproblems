package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 */
public class AssignmentDbRepository implements Repository<Long, Assignment> {
    private Validator<Assignment> validator;
    private String url;
    private String username;
    private String password;

    public AssignmentDbRepository(Validator<Assignment> validator,
                                  String url,
                                  String username,
                                  String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Assignment> findOne(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Assignment> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        throw new NotImplementedException();
    }
}
