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
        /** Version 1 */
//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        return Optional.ofNullable(entities.get(id));

        /** Version 2 */
        try {
            Objects.requireNonNull(id);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.get(id));

        /** Version 2.1 (experimental) */
//        Optional<ID> opt = Optional.ofNullable(id);
//        opt.orElseThrow(IllegalArgumentException::new);
//        return Optional.ofNullable(entities.get(opt.get()));
    }

    @Override
    public Iterable<T> findAll() {
        return entities.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        /** Version 1 */
//        if (entity == null) {
//            throw new IllegalArgumentException("entity must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

        /** Version 2 */
        try {
            Objects.requireNonNull(entity);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

        /** Version 2.1 (experimental) */
//        Optional<T> opt = Optional.ofNullable(entity);
//        opt.orElseThrow(IllegalArgumentException::new);
//        opt.ifPresent(e -> validator.validate(e));
//        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        /** Version 1 */
//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        return Optional.ofNullable(entities.remove(id));

        /** Version 2 */
        try {
            Objects.requireNonNull(id);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.remove(id));

        /** Version 2.1 (experimental) */
//        Optional<ID> opt = Optional.ofNullable(id);
//        opt.orElseThrow(IllegalArgumentException::new);
//        return Optional.of(entities.remove(opt.get()));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        /** Version 1 */
//        if (entity == null ) {
//            throw new IllegalArgumentException("entity must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));

        /** Version 2 */
        try {
            Objects.requireNonNull(entity);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("entity must not be null");
        }

        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));

        /** Version 2.1 (experimental) */
//        Optional<T> opt = Optional.ofNullable(entity);
//        opt.orElseThrow(IllegalArgumentException::new);
//        opt.ifPresent(e -> validator.validate(e));
//        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> opt.get()));
    }
}
