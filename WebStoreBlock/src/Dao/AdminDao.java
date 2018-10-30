package Dao;

import bean.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    boolean selectSameNameByUsername(String username) throws SQLException;

    boolean registerDataToDB(String username, String password) throws SQLException;

    List<Admin> getAllAdminInDB() throws SQLException;

    List<Admin> getPartAdminInDB(int limit, int offset) throws SQLException;

    boolean modifyAdminPasswordInDB(String username, String password) throws SQLException;

    boolean matchAdminUserAndPassInDB(String username, String password) throws SQLException;
}
