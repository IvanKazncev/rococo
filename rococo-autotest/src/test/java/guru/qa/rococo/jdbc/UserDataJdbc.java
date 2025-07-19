package guru.qa.rococo.jdbc;

import guru.qa.rococo.entity.user.UserAuthEntity;
import guru.qa.rococo.entity.user.UserDataEntity;
import guru.qa.rococo.settings.DataConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDataJdbc {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String checkUserByUserName(String userName) throws SQLException {
        String userNameInDb = "";
        try (Connection connection = DbConnection.connection(
                DataConfig.USER_DATA_JDBC_DATA.getUrl(),
                DataConfig.USER_DATA_JDBC_DATA.getLogin(),
                DataConfig.USER_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users where username = ?"
            )) {
                preparedStatement.setString(1, userName);
                preparedStatement.execute();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        userNameInDb = resultSet.getString("username");
                    }
                }
            }
        }
        return userNameInDb;
    }

    public void addUser(@NotNull UserAuthEntity user) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.AUTH_DATA_JDBC_DATA.getUrl(),
                DataConfig.AUTH_DATA_JDBC_DATA.getLogin(),
                DataConfig.AUTH_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                               insert into "user" (id,account_non_expired,account_non_locked,credentials_non_expired,enabled,password,username) 
                                                           values (?, ?,?,?,?,?,?)
                            """
            )) {
                preparedStatement.setObject(1, user.getId());
                preparedStatement.setBoolean(2, user.getAccountNonExpired());
                preparedStatement.setBoolean(3, user.getAccountNonLocked());
                preparedStatement.setBoolean(4, user.getCredentialsNonExpired());
                preparedStatement.setBoolean(5, user.getEnabled());
                preparedStatement.setString(6, "{bcrypt}" + passwordEncoder.encode(user.getPassword()));
                preparedStatement.setString(7, user.getUsername());
                preparedStatement.execute();
            }
        }
    }

    public void addAuthority(@NotNull UserAuthEntity user) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.AUTH_DATA_JDBC_DATA.getUrl(),
                DataConfig.AUTH_DATA_JDBC_DATA.getLogin(),
                DataConfig.AUTH_DATA_JDBC_DATA.getPassword()
        )) {
            for (int i = 0; i < user.getAuthorities().size(); i++) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        """
                                insert into authority (id,authority,user_id) 
                                                                values (?, ?,?)
                                """
                )) {
                    preparedStatement.setObject(1, UUID.randomUUID());
                    preparedStatement.setString(2, user.getAuthorities().get(i).getAuthority().toString());
                    preparedStatement.setObject(3, user.getId());
                    preparedStatement.execute();
                }
            }
        }
    }

    public void addUserToUserData(@NotNull UserDataEntity user) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.USER_DATA_JDBC_DATA.getUrl(),
                DataConfig.USER_DATA_JDBC_DATA.getLogin(),
                DataConfig.USER_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                               insert into "users" (id,username,firstname,lastname,avatar) 
                                                           values (?, ?,?,?,?)
                            """
            )) {
                preparedStatement.setObject(1, UUID.randomUUID());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getFirstname());
                preparedStatement.setString(4, user.getLastname());
                preparedStatement.setString(5, user.getAvatar());
                preparedStatement.execute();
            }
        }
    }

    public void deleteUserFromUserData(String userName) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.USER_DATA_JDBC_DATA.getUrl(),
                DataConfig.USER_DATA_JDBC_DATA.getLogin(),
                DataConfig.USER_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from users where username = ?"
            )) {
                preparedStatement.setString(1, userName);
                preparedStatement.execute();
            }
        }
    }

    public void deleteUserFromAuth(String userName) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.AUTH_DATA_JDBC_DATA.getUrl(),
                DataConfig.AUTH_DATA_JDBC_DATA.getLogin(),
                DataConfig.AUTH_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            delete from "user" where username = ?"""
            )) {
                preparedStatement.setString(1, userName);
                preparedStatement.execute();
            }
        }
    }

    public UUID getUserIdByUsername(String userName) throws SQLException {
        UUID uuid = null;
        try (Connection connection = DbConnection.connection(
                DataConfig.AUTH_DATA_JDBC_DATA.getUrl(),
                DataConfig.AUTH_DATA_JDBC_DATA.getLogin(),
                DataConfig.AUTH_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select * from "user" where username = ?
                            """
            )) {
                preparedStatement.setString(1, userName);
                preparedStatement.execute();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        uuid = resultSet.getObject("id", UUID.class);
                    }
                }
            }
        }
        return uuid;
    }

    public void deleteAuthorityFromAuth(@NotNull UUID userId) throws SQLException {
        try (Connection connection = DbConnection.connection(
                DataConfig.AUTH_DATA_JDBC_DATA.getUrl(),
                DataConfig.AUTH_DATA_JDBC_DATA.getLogin(),
                DataConfig.AUTH_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from authority where user_id = ?"
            )) {
                preparedStatement.setObject(1, userId);
                preparedStatement.execute();
            }
        }
    }

    public UserDataEntity getUserDataByUserName(String userName) throws SQLException {
        UserDataEntity userData = new UserDataEntity();
        try (Connection connection = DbConnection.connection(
                DataConfig.USER_DATA_JDBC_DATA.getUrl(),
                DataConfig.USER_DATA_JDBC_DATA.getLogin(),
                DataConfig.USER_DATA_JDBC_DATA.getPassword()
        )) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users where username = ?"
            )) {
                preparedStatement.setString(1, userName);
                preparedStatement.execute();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        userData.setFirstname(resultSet.getString("firstname"));
                        userData.setLastname(resultSet.getString("lastname"));
                        userData.setAvatar(resultSet.getString("avatar"));
                    }
                }
            }
        }
        return userData;
    }

}
