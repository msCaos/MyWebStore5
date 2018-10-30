package Dao.impls;

import Dao.OrderDao;
import bean.OrderItems;
import bean.Orders;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpls  implements OrderDao {
    static QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }

    @Override
    public boolean insertItemInToDB(OrderItems orderItems) throws SQLException {
        int update = queryRunner.update("insert into orderitem values(null,?,?,?);", orderItems.getOid(), orderItems.getPid(), orderItems.getBuynum());
        System.out.println("*********************insertItemInToDB*******插入返回结果为"+update);
        if (update==1){
            return true;
        }
        return false;
    }

    @Override
    public int InsertOrderToDB(Orders orders) throws SQLException {
        int update = queryRunner.update("insert into orders values(?,?,?,?,?,?,?,?);", orders.getOid(),
                orders.getMoney(), orders.getRecipients(), orders.getTel(), orders.getAddress(),
                orders.getState(), orders.getOrdertime(), orders.getUid());
        System.out.println("*********************Order的插入结果返回值*************插入返回结果为"+update);
        return update;
    }

    @Override
    public List<Orders> getAllMyOrdersInDB(int uid) throws SQLException {
        List<Orders> ordersList = queryRunner.query("select * from orders where uid=?;", uid, new BeanListHandler<Orders>(Orders.class));
        System.out.println("*********************数据库中查询单个用户的所有订单为**********************="+ordersList);
        return ordersList;
    }

    @Override
    public boolean deleteProductInCartByCartidAndPidInDB(int cartid, String pid) throws SQLException {
        int update = queryRunner.update("DELETE FROM item WHERE  cartid=? AND pid=?;", cartid, pid);
        System.out.println("*********************数据库订单操作中删除购物车下订单了的商品结果为**********************="+update);
        if (update==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean changeOrderStateByOidInDB(String oid, int orderState) throws SQLException {
        int update = queryRunner.update("update orders set state=? where oid=?;", orderState, oid);
        System.out.println("*********************数据库订单操作中更改订单状态的结果为**********************="+update);
        if (update==1){
            return true;
        }
        return false;
    }

    @Override
    public List<Orders> getAllUsersOrdersInfoInDB() throws SQLException {
        List<Orders> ordersList = queryRunner.query("select * from orders;", new BeanListHandler<Orders>(Orders.class));
        System.out.println("****************管理员查看所有用户订单的结果****************"+ordersList);
        return ordersList;
    }

    @Override
    public List<Orders> getOrdersLimit(int limit, int offset) throws SQLException {
        List<Orders> limitOrderList = queryRunner.query("select * from orders limit ?  offset ?", new Object[]{limit, offset}, new BeanListHandler<Orders>(Orders.class));
        System.out.println("****************管理员查看limit到offset的订单的结果****************"+limitOrderList);
        return limitOrderList;
    }

    @Override
    public List<OrderItems> getOrderDetailsByOidInDB(String oid) throws SQLException {
        List<OrderItems> itemsList = queryRunner.query("select * from orderitem where oid=?", oid, new BeanListHandler<OrderItems>(OrderItems.class));
        return itemsList;
    }

    @Override
    public boolean deleteOrderByOidInDB(String oid) throws SQLException {
        int update = queryRunner.update("delete from orders where oid=?;", oid);
        if (update==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrderItemByPidAadOidInDB(String oid, int productId) throws SQLException {
        int update = queryRunner.update("delete from orderitem where oid=? and pid=?;", oid, productId);
        if (update==1){
            return true;
        }
        return false;
    }
}
