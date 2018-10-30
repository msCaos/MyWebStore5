package bean;

public class CartidAndUid {
    int cartid;
    int uid;

    public CartidAndUid() {
    }

    public CartidAndUid(int cartid, int uid) {
        this.cartid = cartid;
        this.uid = uid;
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

    @Override
    public String toString() {
        return "CartidAndUid{" +
                "cartid=" + cartid +
                ", uid=" + uid +
                '}';
    }
}
