package Dao;

import bean.CartidAndUid;
import bean.Item;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {

    CartidAndUid getCartidByUidInDB(int id) throws SQLException;

    boolean InsertUidIntoCart(int id) throws SQLException;

    int updateItemDB(int cartid, int productId, int num) throws SQLException;

    Item getThisProductCart(int cartid, int productId) throws SQLException;

    int InsertThisProductToCartDB(int cartid, int productId, int num) throws SQLException;

    List<Item> getAllCartProductsByCartidInDB(int cartid) throws SQLException;

    int deleteProductsInCartByItemidAndCartidInDB(int itemId, int cartid) throws SQLException;

    Item getSnumByPidAndCartid(int cartid, int pid) throws SQLException;
}
