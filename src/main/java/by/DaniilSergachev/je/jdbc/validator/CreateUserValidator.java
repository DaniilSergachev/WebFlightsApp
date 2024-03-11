package by.DaniilSergachev.je.jdbc.validator;

import by.DaniilSergachev.je.jdbc.dto.CreateUserDto;
import by.DaniilSergachev.je.jdbc.entity.Gender;
import by.DaniilSergachev.je.jdbc.entity.Role;
import by.DaniilSergachev.je.jdbc.entity.User;
import by.DaniilSergachev.je.jdbc.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    @Getter
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));

        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));

        }
        return validationResult;
    }
}
