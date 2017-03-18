package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by vlad on 18.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type
 *      {@code T extends BaseEntity<ID>} while maintaining file persistence
 */
public abstract class FileRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {
    protected String fileName;

    public FileRepository(Validator<T> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
    }

    protected abstract void loadData();

    protected abstract void saveToFile(T entity);

    private void refreshFile(T dummy) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, "".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.findAll().forEach(this::saveToFile);
    }

    // package-private
    Optional<T> saveInMemory(T entity) throws ValidatorException {
        return super.save(entity);
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optional = super.save(entity);

        optional.orElseGet(() -> {
            this.saveToFile(entity);
            return null;
        });
        return optional.isPresent() ? optional : Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<T> optional = super.delete(id);
        optional.ifPresent(this::refreshFile);
        return optional.isPresent() ? optional : Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Optional<T> optional = super.update(entity);
        optional.ifPresent(this::refreshFile);
        return optional.isPresent() ? optional : Optional.empty();
    }
}
