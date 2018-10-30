package bean;

public class ProductDB {
    int pid;
    String pname;
    float estoreprice;
    float markprice;
    int pnum;
    String cname;
    String imgurl;
    String description;

    public ProductDB() {
    }

    public ProductDB(int pid, String pname, float estoreprice, float markprice, int pnum, String cname, String imgurl, String description) {
        this.pid = pid;
        this.pname = pname;
        this.estoreprice = estoreprice;
        this.markprice = markprice;
        this.pnum = pnum;
        this.cname = cname;
        this.imgurl = imgurl;
        this.description = description;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public float getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(float estoreprice) {
        this.estoreprice = estoreprice;
    }

    public float getMarkprice() {
        return markprice;
    }

    public void setMarkprice(float markprice) {
        this.markprice = markprice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDB{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", estoreprice=" + estoreprice +
                ", markprice=" + markprice +
                ", pnum=" + pnum +
                ", cname='" + cname + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
