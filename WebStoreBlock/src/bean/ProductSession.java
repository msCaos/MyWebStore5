package bean;

public class ProductSession {
    String pid ;
    String cid ;
    String pname;
    String minprice;
    String maxprice ;
    String num ;

    public ProductSession() {
    }

    public ProductSession(String pid, String cid, String pname, String minprice, String maxprice, String num) {
        this.pid = pid;
        this.cid = cid;
        this.pname = pname;
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.num = num;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
