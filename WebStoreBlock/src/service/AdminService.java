package service;

import Dao.impls.AdminDaoImpls;
import bean.Admin;
import utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class AdminService {
    AdminDaoImpls adminDaoImpls = new AdminDaoImpls();
    public boolean checkRegisterName(String username) throws SQLException {
        boolean res = adminDaoImpls.selectSameNameByUsername(username);
        return res;
    }

    public boolean registerData(String username, String password) throws SQLException {
        boolean res = adminDaoImpls.registerDataToDB(username, password);
        return res;
    }

    public PageHelper<Admin> findAllAdminByNum(String num) throws SQLException {
        int currentPageNum =0;
        try {
            currentPageNum = Integer.parseInt(num);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        List<Admin> list = adminDaoImpls.getAllAdminInDB();
        int totalRecordsNum =list.size();
        int PAGE_COUNT=3;
        PageHelper<Admin> pageHelper = new PageHelper<>(currentPageNum,totalRecordsNum,PAGE_COUNT);
        int limit = PAGE_COUNT;
        int offset=(currentPageNum-1)*PAGE_COUNT;

        //查询出错！
        List<Admin> partAdminInDB = adminDaoImpls.getPartAdminInDB(limit, offset);
        System.out.println("ffindAllAdminByNum中的partProduInService="+partAdminInDB);
        pageHelper.setRecords(partAdminInDB);
        return pageHelper;
    }

    public boolean modifyAdminPassword(String username, String password) throws SQLException {
        boolean res = adminDaoImpls.modifyAdminPasswordInDB(username,password);
        return  res;
    }

    public boolean matchAdminUsernameAndPassword(String username, String password) throws SQLException {
        boolean res = adminDaoImpls.matchAdminUserAndPassInDB(username,password);
        return res;
    }
}
