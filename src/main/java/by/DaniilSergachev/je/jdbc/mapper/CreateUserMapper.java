package by.DaniilSergachev.je.jdbc.mapper;

import by.DaniilSergachev.je.jdbc.dto.CreateUserDto;
import by.DaniilSergachev.je.jdbc.dto.UserDto;
import by.DaniilSergachev.je.jdbc.entity.Gender;
import by.DaniilSergachev.je.jdbc.entity.Role;
import by.DaniilSergachev.je.jdbc.entity.User;
import by.DaniilSergachev.je.jdbc.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.*;


@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto>{
    @Getter
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public  User mapFrom(CreateUserDto object) {
        return User.builder().
                name(object.getName())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .role(Role.valueOf(object.getRole()))
                .build();
    }
}
