package ro.droptable.labproblems.repository;

import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.Validator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.util.XmlReader;
import ro.droptable.labproblems.util.XmlWriter;

import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 07.03.2017.
 *
 * Extension of {@code InMemoryRepository} for CRUD operations on a repository for type {@code Student}
 *      while maintaining XML persistence
 */
public class StudentXmlRepository extends InMemoryRepository<Long, Student> {
    private String fileName;

    public StudentXmlRepository(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        List<Student> students = new XmlReader<Long, Student>(fileName).loadEntities();
        // TODO: implement this using streams
        for (Student student : students) {
            try {
                super.save(student);
            } catch (ValidatorException e) {
                e.printStackTrace(); // TODO: do something else
            }
        }
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        Optional<Student> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        new XmlWriter<Long, Student>(fileName).save(entity);
        return Optional.empty();
    }
}
