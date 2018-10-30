package Dao.impls;

import Dao.CartDao;
import bean.CartidAndUid;
import bean.Item;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

public class CartDaoImpls  implements CartDao {

    static QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }

    @Override
    public CartidAndUid getCartidByUidInDB(int id) throws SQLException {
        CartidAndUid cartList = queryRunner.query("select * from cart where uid=?", id, new BeanHandler<CartidAndUid>(CartidAndUid.class));
        System.out.println("*******************数据库*************getCartidByUidInDB="+cartList);
        return cartList;
    }

    @Override
    public boolean InsertUidIntoCart(int id) throws SQLException {
        int update = queryRunner.update("insert into cart values(null,?);", id);
        if (update==1){
            System.out.println("***************InsertUidIntoCart**********"+update);
            return  true;
        }
        return false;
    }

    @Override
    public int updateItemDB(int cartid, int productId, int num) throws SQLException {
        int update = queryRunner.update("update item set snum=snum+? where cartid=? and pid=?", num, cartid, productId);
        System.out.println("***************updateItemDB**********"+update);
        return update;
    }

    @Override
    public Item getThisProductCart(int cartid, int productId) throws SQLException {
        Item item = queryRunner.query("select * from item where cartid=? and pid=?;", new Object[]{cartid,
                productId}, new BeanHandler<Item>(Item.class));
        return item;
    }

    @Override
    public int InsertThisProductToCartDB(int cartid, int productId, int num) throws SQLException {
        int update = queryRunner.update("insert into item values(null,?,?,?);", cartid, productId, num);
        return update;
    }

    @Override
    public List<Item> getAllCartProductsByCartidInDB(int cartid) throws SQLException {
        List<Item> itemList = queryRunner.query("select * from item where cartid=?;", cartid, new BeanListHandler<Item>(Item.class));
        return itemList;
    }

    @Override
    public int deleteProductsInCartByItemidAndCartidInDB(int itemId, int cartid) throws SQLException {
        int update = queryRunner.update("delete  from item where itemid=? and cartid=?", itemId, cartid);
        System.out.println("**************数据库删除购物车一个商品****************update"+update);
        return update;
    }

    @Override
    public Item getSnumByPidAndCartid(int cartid, int pid) throws SQLException {
        Item item = queryRunner.query("select * from item where cartid=? and pid=?", new Object[]{cartid, pid}, new BeanHandler<Item>(Item.class));
        System.out.println("*********这里是订单从购物车查询商品数量信息snum************"+item);
        return item;
    }
}
