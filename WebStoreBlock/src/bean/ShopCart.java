package bean;

import java.util.List;

public class ShopCart {
    int cartid;
    int uid;
    List<Item> itemList;

    public ShopCart() {
    }

    public ShopCart(int cartid, int uid, List<Item> itemList) {
        this.cartid = cartid;
        this.uid = uid;
        this.itemList = itemList;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "cartid=" + cartid +
                ", uid=" + uid +
                ", itemList=" + itemList +
                '}';
    }
}
