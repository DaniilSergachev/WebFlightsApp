package by.DaniilSergachev.je.jdbc.dto;

import by.DaniilSergachev.je.jdbc.entity.Gender;
import by.DaniilSergachev.je.jdbc.entity.Role;
import lombok.*;

import java.time.LocalDate;


@Value
@Builder
public class UserDto {
    int id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;
}
