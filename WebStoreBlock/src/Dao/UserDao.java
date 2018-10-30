package Dao;

import bean.User;

import java.sql.SQLException;

public interface UserDao {
    boolean registerUserInfoToDB(User user) throws SQLException;

    User matchUserLogIn(String username, String password) throws SQLException;

    User getUserByUsernameAndPasswordInDB(User user) throws SQLException;

    boolean updateUserInfoInDB(User user) throws SQLException;

    User getUserByUidInDB(int id) throws SQLException;

    User getUerByUsernameInDB(String username) throws SQLException;
}
