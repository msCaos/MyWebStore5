package service;

import Dao.impls.UserDaoImpls;
import bean.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    UserDaoImpls userDaoImpls = new UserDaoImpls();
    public boolean registerUserInfo(User user) throws SQLException {
        boolean res = userDaoImpls.registerUserInfoToDB(user);

        return res;
    }

    public boolean matchUsernameAndPassword(String username, String password) throws SQLException {
        User user = userDaoImpls.matchUserLogIn(username,password);
        return false;
    }

    public User getUserByUsernameAndPassword(User user) throws SQLException {
        User res = userDaoImpls.getUserByUsernameAndPasswordInDB(user);
        return res;
    }

    public boolean modifyUserInfo(User user) throws SQLException {
        boolean res = userDaoImpls.updateUserInfoInDB(user);
        return res;
    }

    public User getUserByUid(int id) throws SQLException {
        User user = userDaoImpls.getUserByUidInDB(id);
        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        User user = userDaoImpls.getUerByUsernameInDB(username);
        return user;
    }
}
