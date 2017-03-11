package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Implementation of {@code Repository} for CRUD operations on a repository for {@code Problem}
 *      while maintaining database persistence
 */
public class ProblemDbRepository implements Repository<Long, Problem> {
    private Validator<Problem> validator;
    private String url;
    private String username;
    private String password;

    public ProblemDbRepository(Validator<Problem> validator,
                               String url,
                               String username,
                               String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Problem> findOne(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Problem> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Problem> delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        throw new NotImplementedException();
    }
}
