package ro.droptable.labproblems.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.droptable.labproblems.common.service.ProblemService;
import ro.droptable.labproblems.common.domain.Problem;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;
import ro.droptable.labproblems.server.repository.ProblemDbRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by stefana on 3/28/2017.
 */

public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemDbRepository repository;

    /**
     * {@inheritDoc}
     */
    public void addProblem(String title, String description) throws ValidatorException {
        Class problemClass;

        try {
            problemClass = Class.forName("ro.droptable.labproblems.common.domain.Problem");
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

    /**
     * {@inheritDoc}
     */
    public void updateProblem(Long id, String title, String description) throws NoSuchElementException, ValidatorException
    {
        Problem oldProblem = findOneProblem(id).get(); //throws NoSuchElementException if the old problem does not exist
        Class problemClass;

        try {
            problemClass = Class.forName("ro.droptable.labproblems.common.domain.Problem");
            Problem problemInstance = (Problem) problemClass.newInstance();

            //create a new instance with the same id - do not modify current id
            Field idField = problemClass.getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(problemInstance, id);
            idField.setAccessible(false);


            Field titleField = problemClass.getDeclaredField("title");
            titleField.setAccessible(true);
            titleField.set(problemInstance, title);
            titleField.setAccessible(false);

            Field descriptionField = problemClass.getDeclaredField("description");
            descriptionField.setAccessible(true);
            descriptionField.set(problemInstance, description);
            descriptionField.setAccessible(false);

            this.repository.update(problemInstance);

        } catch (ClassNotFoundException |
                IllegalAccessException  |
                InstantiationException  |
                NoSuchFieldException e)
        {
            e.printStackTrace(); // TODO: do something else
        }

    }

    /**
     * {@inheritDoc}
     */
    public Set<Problem> filterProblemsByTitle(String title) {
        Iterable<Problem> problems = repository.findAll();
        Set<Problem> filteredProblems = new HashSet<>();
        problems.forEach(filteredProblems::add);
        filteredProblems.removeIf(pr -> !pr.getTitle().contains(title));
        return filteredProblems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Problem> findOneProblem(Long id) {
        return repository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Problem> findAllProblems() {
        Iterable<Problem> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProblem(Long id) {
        repository.delete(id);
    }
}
