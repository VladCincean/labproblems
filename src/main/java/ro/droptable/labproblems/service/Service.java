package ro.droptable.labproblems.service;
import ro.droptable.labproblems.repository.Repository;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.domain.BaseEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/5/2017.
 */
public abstract class Service<T extends BaseEntity<Long>>{
    protected Repository<Long, T > repository;

    public Service(){}

    public Service(Repository<Long, T > repository) {
        this.repository = repository;
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     *
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public void delete(Long id) throws ValidatorException{
        repository.delete(id);
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public Optional<T> findOne(Long id){
        return repository.findOne(id);
    }

    /**
     *
     * @return all entities.
     */
    public Set<T > getAll() {
        Iterable<T > entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    public Optional<T> getByAttributes(T comp) {
        Iterable<T> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).filter(e -> e.equals(comp)).findFirst();
    }

}
