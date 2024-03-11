package by.DaniilSergachev.je.jdbc.mapper;

import by.DaniilSergachev.je.jdbc.dto.UserDto;
import by.DaniilSergachev.je.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper <UserDto,User> {
    @Getter
    private static final UserMapper INSTANCE = new UserMapper();
    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender()).build();

    }
}
