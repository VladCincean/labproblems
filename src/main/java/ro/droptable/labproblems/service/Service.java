package ro.droptable.labproblems.service;
import ro.droptable.labproblems.repository.Repository;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.domain.BaseEntity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/5/2017.
 */
abstract public class Service{
    protected Repository<Long, BaseEntity<Long> > repository;

    public Service(){}

    public Service(Repository<Long, BaseEntity<Long> > repository) {
        this.repository = repository;
    }

    public void add(BaseEntity<Long>  entity) throws ValidatorException {
        repository.save(entity);
    }

    public Set<BaseEntity<Long> > getAll() {
        Iterable<BaseEntity<Long> > entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

}
