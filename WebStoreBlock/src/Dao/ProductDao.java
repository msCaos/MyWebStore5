package Dao;

import bean.ProductDB;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public boolean insertInfoInToDB(ProductDB productDb);

    public List<ProductDB> findAllInfoInDB() throws SQLException;

    public List<ProductDB> getPartProductInDB(int limit,int offset) throws SQLException;

    public boolean deleteOneInfoInDB(int pid) throws SQLException;

    public ProductDB selectProductByPidInDB(int pid) throws SQLException;

    List<ProductDB> getProductInConditionInDB(String pid, String cname, String pname, String  minprice, String maxprice) throws SQLException;

    List<ProductDB> getProductLimit(String pid, String cname, String pname, String minprice, String maxprice, int limit, int offset) throws SQLException;

    boolean modifyProductInfoInDB(ProductDB productDB) throws SQLException;

    List<ProductDB> getAllProductByCname(String cname) throws SQLException;

    ProductDB getOneProductByPidInDB(int id) throws SQLException;

    List<ProductDB> getAllProductsBySearchPname(String pname) throws SQLException;
}

