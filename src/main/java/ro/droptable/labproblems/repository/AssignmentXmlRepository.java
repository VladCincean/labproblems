package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlReader;
import ro.droptable.labproblems.util.XmlWriter;

import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Assignment}
 *      while maintaining XML persistence
 */
public class AssignmentXmlRepository extends  InMemoryRepository<Long, Assignment> {
    private String fileName;

    public AssignmentXmlRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        List<Assignment> assignments = new XmlReader<Long, Assignment>(fileName).loadEntities();
        // TODO: implement this using streams
        for (Assignment assignment : assignments) {
            try {
                super.save(assignment);
            } catch (ValidatorException e) {
                e.printStackTrace(); // TODO: do something else
            }
        }
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        new XmlWriter<Long, Assignment>(fileName).save(entity);
        return Optional.empty();
    }
}
