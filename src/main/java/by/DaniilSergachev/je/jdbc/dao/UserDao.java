package by.DaniilSergachev.je.jdbc.dao;

import by.DaniilSergachev.je.jdbc.entity.Gender;
import by.DaniilSergachev.je.jdbc.entity.Role;
import by.DaniilSergachev.je.jdbc.entity.User;
import by.DaniilSergachev.je.jdbc.utils.ConnectionManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long,User> {
    @Getter
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender)
            values (?,?,?,?,?,?)
            """;

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * FROM users where email = ? and password = ?
            """;
    @Override
    public boolean update(User user) {
        return false;
    }

    public Optional<User> GetByEmailAndPasswordSQL(String email, String password){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)){
            statement.setString(1, email);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id",Integer.class))
                .name(resultSet.getObject("name",String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email",String.class))
                .password(resultSet.getObject("password",String.class))
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null)).
                gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .build();
    }


    @Override
    public User save(User user) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setObject(2, user.getBirthday());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setObject(5, user.getRole().name());
            statement.setObject(6, user.getGender().name());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next())
                user.setId(keys.getObject("id",Integer.class));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

}
