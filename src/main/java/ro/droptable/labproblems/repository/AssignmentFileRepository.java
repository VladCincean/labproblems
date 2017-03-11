package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining 'text file' persistence
 */
public class AssignmentFileRepository extends InMemoryRepository<Long, Assignment>  {
    private String fileName;

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Assignment entity) {
        throw new NotImplementedException();
    }
}
