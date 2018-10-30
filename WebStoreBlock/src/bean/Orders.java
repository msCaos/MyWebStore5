package bean;

public class Orders {
    String oid;
    float money;
    String recipients;
    String tel;
    String address;
    int state;
    String ordertime;
    int uid;
    User user;

    public Orders() {
    }

    public Orders(String oid, float money, String recipients, String tel, String address, int state, String ordertime, int uid, User user) {
        this.oid = oid;
        this.money = money;
        this.recipients = recipients;
        this.tel = tel;
        this.address = address;
        this.state = state;
        this.ordertime = ordertime;
        this.uid = uid;
        this.user = user;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", money=" + money +
                ", recipients='" + recipients + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", ordertime='" + ordertime + '\'' +
                ", uid=" + uid +
                ", user=" + user +
                '}';
    }
}
