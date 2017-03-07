package ro.droptable.labproblems.domain.validators;

/**
 * @author vlad
 *
 * Interface for a generic {@code Validator} that validates a specific tyle
 */
public interface Validator<T> {
    /**
     * Validates a {@code T} instance object
     * @param entity - the {@code T} instance object that needs to be validated
     * @throws ValidatorException - if {@code entity} is not valid
     */
    void validate(T entity) throws ValidatorException;
}
