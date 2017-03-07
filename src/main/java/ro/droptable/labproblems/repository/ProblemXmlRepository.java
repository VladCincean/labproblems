package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlReader;
import ro.droptable.labproblems.util.XmlWriter;

import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 */
public class ProblemXmlRepository extends  InMemoryRepository<Long, Problem> {
    private String fileName;

    public ProblemXmlRepository(Validator<Problem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        List<Problem> problems = new XmlReader<Long, Problem>(fileName).loadEntities();
        // TODO: implement this using streams
        for (Problem problem : problems) {
            try {
                super.save(problem);
            } catch (ValidatorException e) {
                e.printStackTrace(); // TODO: do something else
            }
        }
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        new XmlWriter<Long, Problem>(fileName).save(entity);
        return Optional.empty();
    }
}
