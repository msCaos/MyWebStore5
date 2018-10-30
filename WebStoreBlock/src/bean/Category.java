package bean;

public class Category {
    String id;
    String cname;

    public Category() {
    }

    public Category(String id, String cname) {
        this.id = id;
        this.cname = cname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
