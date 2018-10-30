package bean;

public class Item {
    int itemid;
    int cartid;
    int pid;
    int snum;
    Product product;

    public Item() {
    }

    public Item(int itemid, int snum, int cartid, int pid, Product product) {
        this.itemid = itemid;
        this.snum = snum;
        this.cartid = cartid;
        this.pid = pid;
        this.product = product;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemid=" + itemid +
                ", cartid=" + cartid +
                ", pid=" + pid +
                ", snum=" + snum +
                ", product=" + product +
                '}';
    }
}
