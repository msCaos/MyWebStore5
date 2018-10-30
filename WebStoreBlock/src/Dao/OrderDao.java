package Dao;

import bean.OrderItems;
import bean.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean insertItemInToDB(OrderItems orderItems) throws SQLException;

    int InsertOrderToDB(Orders orders) throws SQLException;

    List<Orders> getAllMyOrdersInDB(int uid) throws SQLException;

    boolean deleteProductInCartByCartidAndPidInDB(int cartid, String pid) throws SQLException;

    boolean changeOrderStateByOidInDB(String oid, int orderState) throws SQLException;

    List<Orders> getAllUsersOrdersInfoInDB() throws SQLException;

    List<Orders> getOrdersLimit(int limit, int offset) throws SQLException;

    List<OrderItems> getOrderDetailsByOidInDB(String oid) throws SQLException;

    boolean deleteOrderByOidInDB(String oid) throws SQLException;

    boolean deleteOrderItemByPidAadOidInDB(String oid, int productId) throws SQLException;
}
