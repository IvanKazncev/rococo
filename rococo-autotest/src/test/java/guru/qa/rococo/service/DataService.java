package guru.qa.rococo.service;

import guru.qa.rococo.entity.user.UserAuthEntity;
import guru.qa.rococo.entity.user.UserDataEntity;
import guru.qa.rococo.jdbc.UserDataJdbc;

import java.sql.SQLException;

public class DataService {

    UserDataJdbc userDataJdbc = new UserDataJdbc();;

    public String findUserByName(String userName) throws SQLException {
        return userDataJdbc.checkUserByUserName(userName);
    }

    public void addUser(UserAuthEntity user, UserDataEntity userData) throws SQLException {
        userDataJdbc.addUser(user);
        userDataJdbc.addAuthority(user);
        userDataJdbc.addUserToUserData(userData);
    }

    public void deleteUserData(String userName) throws SQLException {
      var userId =   userDataJdbc.getUserIdByUsername(userName);
      userDataJdbc.deleteAuthorityFromAuth(userId);
      userDataJdbc.deleteUserFromAuth(userName);
      userDataJdbc.deleteUserFromUserData(userName);
    }

    public UserDataEntity getUserDataByUserName(String userName) throws SQLException {
        return userDataJdbc.getUserDataByUserName(userName);
    }
}
