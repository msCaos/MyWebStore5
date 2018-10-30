package Dao;

import bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    public boolean addCategoryToDataBase(String cname);

    public boolean selectSameFromDB(String cname);

    public List<Category>  getAllCategoryInDB();

    public boolean modifyCategoryInDB(int id,String cname);

    public boolean deleteThisInfoInDB(String cname);

    public List<Category>  getPartCategoryInDB(int limit,int offset);

    public String getCnameByCid(int id) throws SQLException;

    public String getCidByCname(String cname) throws SQLException;
}
