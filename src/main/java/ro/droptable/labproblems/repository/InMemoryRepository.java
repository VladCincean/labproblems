package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vlad on 04.03.2017.
 *
 * Implementation of {@code Repository} for generic CRUD operations on a repository for a specific type
 *      while maintaining 'in-memory' persistence
 */
public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public Optional<T> findOne(ID id) {
//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        return Optional.ofNullable(entities.get(id));

        try {
            Objects.requireNonNull(id);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return entities.entrySet().stream()
                .map(Map.Entry::getValue)
//                .map(entry -> entry.getValue()) // TODO: delete this line
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
//        if (entity == null) {
//            throw new IllegalArgumentException("entity must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

        try {
            Objects.requireNonNull(entity);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        return Optional.ofNullable(entities.remove(id));

        try {
            Objects.requireNonNull(id);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
//        if (entity == null ) {
//            throw new IllegalArgumentException("entity must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));

        try {
            Objects.requireNonNull(entity);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
