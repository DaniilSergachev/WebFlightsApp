package by.DaniilSergachev.je.jdbc.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
