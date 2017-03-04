package ro.droptable.labproblems.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
