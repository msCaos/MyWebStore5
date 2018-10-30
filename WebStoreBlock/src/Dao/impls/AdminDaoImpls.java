package Dao.impls;

import Dao.AdminDao;
import bean.Admin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpls  implements AdminDao {
    static QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }

    @Override
    public boolean selectSameNameByUsername(String username) throws SQLException {
        boolean flag=false;
        List<Admin> list = queryRunner.query("select * from administrator where username=?", username, new BeanListHandler<Admin>(Admin.class));
        System.out.println("list="+list);
        if (!list.isEmpty()){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean registerDataToDB(String username, String password) throws SQLException {
        boolean flag=false;
        int update = queryRunner.update("insert into administrator  values(null,?,?);", new Object[]{username, password});
        if (update==1){
            flag=true;
        }
        return flag;
    }

    @Override
    public List<Admin> getAllAdminInDB() throws SQLException {
        String sql = "select * from administrator;";
        List<Admin> adminList = queryRunner.query(sql, new BeanListHandler<Admin>(Admin.class));
        System.out.println("getAllAdminInDB中的adminList="+adminList);
        return  adminList;
    }

    @Override
    public List<Admin> getPartAdminInDB(int limit, int offset) throws SQLException {
        String sql ="select * from administrator limit ? offset ?";
        List<Admin> partAdminList = queryRunner.query(sql,new Object[]{limit,offset},new BeanListHandler<Admin>(Admin.class));
        System.out.println("getPartAdminInDB中的adminList="+partAdminList);
        return partAdminList;
    }

    @Override
    public boolean modifyAdminPasswordInDB(String username, String password) throws SQLException {
        String sql="update administrator set password=? where username=?";
        int update = queryRunner.update(sql, password, username);
        if (update==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean matchAdminUserAndPassInDB(String username, String password) throws SQLException {
        List<Admin> adminList = queryRunner.query("select * from administrator where username=? and password=?", new Object[]{username, password}, new BeanListHandler<Admin>(Admin.class));
        if (!adminList.isEmpty()){
            return true;
        }
        return false;
    }
}
