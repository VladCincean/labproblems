package ro.droptable.labproblems.service;

import ro.droptable.labproblems.domain.BaseEntity;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.Repository;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 05.03.2017.
 */
public class ProblemService extends Service<Problem> {
    public ProblemService(Repository<Long, Problem > repository) {
        this.repository = repository;
    }

    /**
     * Saves the given entity.
     *
     * @param title
     *            must not be null.
     * @param description
     *            must not be null.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public void add(String title, String description) throws ValidatorException {
        Class problemClass;

        try {
            problemClass = Class.forName("ro.droptable.labproblems.domain.Problem");
            Problem problemInstance = (Problem)problemClass.newInstance();

            Field idField = problemClass.getSuperclass().getDeclaredField("id");
            Field currentIdField = problemClass.getDeclaredField("currentId");
            idField.setAccessible(true);
            currentIdField.setAccessible(true);
            idField.set(problemInstance, currentIdField.getLong(problemInstance));
            currentIdField.set(problemInstance, currentIdField.getLong(problemInstance) + 1);
            idField.setAccessible(false);
            currentIdField.setAccessible(false);

            Field titleField = problemClass.getDeclaredField("title");
            titleField.setAccessible(true);
            titleField.set(problemInstance, title);
            titleField.setAccessible(false);

            Field descriptionField = problemClass.getDeclaredField("description");
            descriptionField.setAccessible(true);
            descriptionField.set(problemInstance, description);
            descriptionField.setAccessible(false);

            repository.save(problemInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }
    }
}
