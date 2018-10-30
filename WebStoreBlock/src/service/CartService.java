package service;

import Dao.impls.CartDaoImpls;
import bean.CartidAndUid;
import bean.Item;
import bean.Product;

import java.sql.SQLException;
import java.util.List;

public class CartService {
    CartDaoImpls cartDaoImpls = new CartDaoImpls();
    ProductService productService = new ProductService();

    public int getCartidByUid(int uid) throws SQLException {

           CartidAndUid orginalCartid  = cartDaoImpls.getCartidByUidInDB(uid);
           System.out.println("************************CartSercice*******111111********cartOfThisUid1="+orginalCartid);
           if (orginalCartid==null){
               boolean res = cartDaoImpls.InsertUidIntoCart(uid);
               if (res){
                   CartidAndUid newCartid = cartDaoImpls.getCartidByUidInDB(uid);
                   System.out.println("************************CartSercice*******2222********newCartid="+newCartid);
                   return newCartid.getCartid();
               }
           }
           return orginalCartid.getCartid();
    }

    public Item getThisProductInCartByPidAndCatrid(int cartid, int productId) throws SQLException {
        Item productItem= cartDaoImpls.getThisProductCart(cartid,productId);
        return  productItem;
    }

    public Item InsertProductToCart(int cartid, int productId, int num) throws SQLException {
        int update = cartDaoImpls.updateItemDB(cartid,productId,num);
        if (update==1){
            //该产品存在
            Item productItem= this.getThisProductInCartByPidAndCatrid(cartid,productId);
            System.out.println("*****该产品存在*******InsertProductToCart****"+productItem);
            return productItem;
        }else{
            //该产品之前不存在
            int result = cartDaoImpls.InsertThisProductToCartDB(cartid,productId,num);
            if (result==1){
                Item thisItemProduct = this.getThisProductInCartByPidAndCatrid(cartid, productId);
                System.out.println("************该产品之前不存在**************"+thisItemProduct);
                return thisItemProduct;
            }
        }
        return null;
    }

    public List<Item> getAllProductsInCartBy(int cartid) throws SQLException {
        List<Item> cartList = cartDaoImpls.getAllCartProductsByCartidInDB(cartid);
        for (Item item:cartList) {
            int pid = item.getPid();
            String productid = String.valueOf(pid);
            Product product = productService.selectProductByPid(productid);
            item.setProduct(product);
        }
        return cartList;
    }

    public boolean deleteProductsInCartByItemidAndCartid(int itemId, int cartid) throws SQLException {
        int update = cartDaoImpls.deleteProductsInCartByItemidAndCartidInDB(itemId,cartid);
        if (update==1){
            return true;
        }
        return false;
    }

    public int getProNumByPidAndCartid(int cartid, int pid) throws SQLException {
        Item item = cartDaoImpls.getSnumByPidAndCartid(cartid,pid);
        if (item!=null){
            System.out.println("********对应商品的数量为*****************"+item.getSnum());
            return item.getSnum();
        }
        return 0;
    }
}
