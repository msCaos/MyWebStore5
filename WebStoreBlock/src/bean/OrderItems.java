package bean;

public class OrderItems {
    int itemid;
    String oid;
    int pid;
    int buynum;

    public OrderItems() {
    }

    public OrderItems(int itemid, String oid, int pid, int buynum) {
        this.itemid = itemid;
        this.oid = oid;
        this.pid = pid;
        this.buynum = buynum;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "itemid=" + itemid +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", buynum=" + buynum +
                '}';
    }
}
