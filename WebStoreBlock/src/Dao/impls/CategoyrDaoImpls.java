package Dao.impls;

import Dao.CategoryDao;
import bean.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class CategoyrDaoImpls implements CategoryDao {
    static  QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }
    @Override
    public boolean addCategoryToDataBase(String cname) {
        boolean flag = false;
        try {
            int update = queryRunner.update("insert into category values(null,?)",cname);
            if (update==1){
                flag=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean selectSameFromDB(String cname) {
        boolean flag = false;
        try {
            Category category = queryRunner.query("select * from category where cname=?", cname, new BeanHandler<Category>(Category.class));
            if (category!=null){
                flag=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Category> getAllCategoryInDB() {
        List<Category> list = null;
        try {
            list = queryRunner.query("select * from category", new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean modifyCategoryInDB(int id, String cname) {
        boolean flag =false;
        try {
            int update = queryRunner.update("update category set cname=? where id=?", cname, id);
            if (update==1){
                flag=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteThisInfoInDB(String cname) {
        boolean flag=false;
        try {
            int update = queryRunner.update("delete  from category where cname=?", cname);
            if (update==1){
                flag=true;
            }
        } catch (SQLException e) {
        }
        return flag;
    }

    @Override
    public List<Category> getPartCategoryInDB(int limit, int offset) {
        List<Category> list =null;
        try {
            list = queryRunner.query("select * from category limit ? offset ?;",new Object[]{limit,offset},new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getCnameByCid(int id) {
        try {
            Category category = queryRunner.query("select * from category where id=?", id, new BeanHandler<Category>(Category.class));
            System.out.println("getCnameByCid中的category="+category);
            return category.getCname();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCidByCname(String cname) throws SQLException {
        Category category = queryRunner.query("select * from category where cname=?", cname, new BeanHandler<Category>(Category.class));
        System.out.println("getCidByCname中的category="+category);
        System.out.println("getCidByCname中的category.id="+category.getId());
        return category.getId();
    }
}
