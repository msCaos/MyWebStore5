package bean;

import java.util.List;

public class Product {
    String cid;
    String pid;
    String pname;
    String estoreprice;
    String markprice;
    String pnum;
    String imgurl;
    String description;
    String cname;
    List<Category> category;

    public Product() {
    }
    public Product(String cid, String pid, String pname, String estoreprice, String markprice, String pnum, String imgurl, String description, String cname) {
        this.cid = cid;
        this.pid = pid;
        this.pname = pname;
        this.estoreprice = estoreprice;
        this.markprice = markprice;
        this.pnum = pnum;
        this.imgurl = imgurl;
        this.description = description;
        this.cname = cname;
    }

    public Product(String pid, String pname, String estoreprice, String markprice, String pnum, String imgurl, String description, List<Category> category) {
        this.pid = pid;
        this.pname = pname;
        this.estoreprice = estoreprice;
        this.markprice = markprice;
        this.pnum = pnum;
        this.imgurl = imgurl;
        this.description = description;
        this.category = category;
    }

    public Product(String cid, String pid, String pname, String estoreprice, String markprice, String pnum, String imgurl, String description, String cname, List<Category> category) {
        this.cid = cid;
        this.pid = pid;
        this.pname = pname;
        this.estoreprice = estoreprice;
        this.markprice = markprice;
        this.pnum = pnum;
        this.imgurl = imgurl;
        this.description = description;
        this.cname = cname;
        this.category = category;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(String estoreprice) {
        this.estoreprice = estoreprice;
    }

    public String getMarkprice() {
        return markprice;
    }

    public void setMarkprice(String markprice) {
        this.markprice = markprice;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
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
        return "Product{" +
                "cid='" + cid + '\'' +
                ", pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", estoreprice='" + estoreprice + '\'' +
                ", markprice='" + markprice + '\'' +
                ", pnum='" + pnum + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", description='" + description + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}


