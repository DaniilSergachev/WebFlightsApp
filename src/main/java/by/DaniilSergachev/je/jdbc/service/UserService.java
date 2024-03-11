package by.DaniilSergachev.je.jdbc.service;

import by.DaniilSergachev.je.jdbc.dao.UserDao;
import by.DaniilSergachev.je.jdbc.dto.CreateUserDto;
import by.DaniilSergachev.je.jdbc.dto.UserDto;
import by.DaniilSergachev.je.jdbc.entity.User;
import by.DaniilSergachev.je.jdbc.exception.ValidationException;
import by.DaniilSergachev.je.jdbc.mapper.CreateUserMapper;
import by.DaniilSergachev.je.jdbc.mapper.UserMapper;
import by.DaniilSergachev.je.jdbc.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getINSTANCE();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getINSTANCE();
    private final UserMapper userMapper = UserMapper.getINSTANCE();
    public Optional<UserDto> login(String email, String password) {
        return userDao.GetByEmailAndPasswordSQL(email,password).map(userMapper::mapFrom);
    }

    public Integer createUser(CreateUserDto createUserDto){
        var validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }

        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }


    public static  UserService getInstance(){
        return INSTANCE;
    }


}
