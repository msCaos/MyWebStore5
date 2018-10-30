package Dao.impls;

import Dao.UserDao;
import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpls implements UserDao {
    static QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }

    @Override
    public boolean registerUserInfoToDB(User user) throws SQLException {
        boolean flag=false;
        String sql = "insert into users values(null,?,?,?,?,?);";
        int update = queryRunner.update(sql, user.getUsername(), user.getNickname(), user.getPassword(),
                user.getEmail(), user.getBirthday());
        if (update==1){
            flag=true;
        }
        return flag;
    }

    @Override
    public User matchUserLogIn(String username, String password) throws SQLException {
        User user = queryRunner.query("select * from users where username=? and password=?;",
                new Object[]{username, password}, new BeanHandler<User>(User.class));
        return user;
    }

    @Override
    public User getUserByUsernameAndPasswordInDB(User user) throws SQLException {
        User userInDb = queryRunner.query("select * from users where username=? and password=?;",
                new Object[]{user.getUsername(), user.getPassword()}, new BeanHandler<User>(User.class));
        return userInDb;
    }

    @Override
    public boolean updateUserInfoInDB(User user) throws SQLException {
        String sql="update users set nickname=?,password=?,email=?,birthday=? where uid=?;";
        int update = queryRunner.update(sql, user.getNickname(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getUid());
        if (update==1){
            System.out.println("*******************修改成功！********************");
            return true;
        }
        return false;
    }

    @Override
    public User getUserByUidInDB(int id) throws SQLException {
        User user = queryRunner.query("select * from users where uid=?", id, new BeanHandler<User>(User.class));
        System.out.println("*******************user="+user);
        return user;
    }

    @Override
    public User getUerByUsernameInDB(String username) throws SQLException {

        User user = queryRunner.query("select * from users where username=?;", username, new BeanHandler<User>(User.class));
        System.out.println("***********getUerByUsernameInDB***匹配是否重名********user="+user);
        return user;
    }
}
