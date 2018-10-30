package Dao.impls;

import Dao.ProductDao;
import bean.ProductDB;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpls implements ProductDao {
    static QueryRunner queryRunner = null;
    static {
        queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    }

    @Override
    public boolean insertInfoInToDB(ProductDB productDb) {
        boolean flag=false;
        try {
            int update = queryRunner.update("insert into product values(?,?,?,?,?,?,?,?);", productDb.getPid(),
                    productDb.getPname(), productDb.getEstoreprice(), productDb.getMarkprice(), productDb.getPnum(),
                    productDb.getCname(), productDb.getImgurl(), productDb.getDescription());
            System.out.println("update="+update);
            if (update==1){
                flag=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<ProductDB> findAllInfoInDB() throws SQLException {
        List<ProductDB> productDBList = queryRunner.query("select * from product;", new BeanListHandler<ProductDB>(ProductDB.class));
        System.out.println("productDBList="+productDBList);
        return productDBList;
    }

    @Override
    public List<ProductDB> getPartProductInDB(int limit, int offset) throws SQLException {
        List<ProductDB> productDBList = queryRunner.query("select * from product limit ? offset ? ", new Object[]{limit,offset}, new BeanListHandler<ProductDB>(ProductDB.class));
        System.out.println("productDBListInDB="+productDBList);
        return productDBList;
    }

    @Override
    public boolean deleteOneInfoInDB(int pid) throws SQLException {
        int update = queryRunner.update("delete  from product where pid=?", pid);
        System.out.println("update="+update);
        boolean flag=false;
        if (update==1){
            flag=true;
        }
        return flag;
    }

    @Override
    public ProductDB selectProductByPidInDB(int pid) throws SQLException {
        ProductDB productDB = queryRunner.query("select * from product where pid=?", pid, new BeanHandler<ProductDB>(ProductDB.class));
        System.out.println("productDB="+productDB);
        return productDB;
    }


    @Override
    public List<ProductDB> getProductInConditionInDB(String pid, String cname, String pname, String  minprice, String maxprice) throws SQLException {
        ArrayList prolist = new ArrayList<>();
        String sql="select * from product where 1=1 ";
        try {
            if (pid!=null && ! pid.isEmpty()){
                sql=sql+"and pid=?";
                int id = Integer.parseInt(pid);
                prolist.add(id);
            }
            if (cname!=null && !cname.isEmpty()){
                sql=sql+"and cname=?";
                prolist.add(cname);
            }
            if (pname!=null && !pname.isEmpty()){
                sql=sql+"and pname lile ?";
                pname="%"+pname+"%";
                prolist.add(pname);
            }
            if (minprice!=null && !minprice.isEmpty()){
                sql=sql+"and estoreprice>=?";
                float min = Float.parseFloat(minprice);
                prolist.add(min);
            }
            if (maxprice!=null && !maxprice.isEmpty()){
                sql=sql+"and estoreprice<=?";
                float max = Float.parseFloat(maxprice);
                prolist.add(max);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        System.out.println("查询语句SQL="+sql);
        Object[] objects = prolist.toArray();
        List<ProductDB> productDBList = queryRunner.query(sql, objects, new BeanListHandler<ProductDB>(ProductDB.class));
        System.out.println("getProductInConditionInDB 中 productDBList="+productDBList);
        return productDBList;
    }

    @Override
    public List<ProductDB> getProductLimit(String pid, String cname, String pname, String minprice, String maxprice, int limit, int offset) throws SQLException {
        ArrayList prolist = new ArrayList<>();
        String sql="select * from product where 1=1 ";
        try {
            if (pid!=null && ! pid.isEmpty()){
                sql=sql+"and pid=?";
                int id = Integer.parseInt(pid);
                prolist.add(id);
            }
            if (cname!=null && !cname.isEmpty()){
                sql=sql+"and cname=?";
                prolist.add(cname);
            }
            if (pname!=null && !pname.isEmpty()){
                sql=sql+"and pname lile ?";
                pname="%"+pname+"%";
                prolist.add(pname);
            }
            if (minprice!=null && !minprice.isEmpty()){
                sql=sql+"and estoreprice>=?";
                float min = Float.parseFloat(minprice);
                prolist.add(min);
            }
            if (maxprice!=null && !maxprice.isEmpty()){
                sql=sql+"and estoreprice<=?";
                float max = Float.parseFloat(maxprice);
                prolist.add(max);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        sql=sql+"limit ? offset ?";
        System.out.println("查询语句SQL="+sql);
        prolist.add(limit);
        prolist.add(offset);
        Object[] objects = prolist.toArray();
        List<ProductDB> productLimitList = queryRunner.query(sql, objects, new BeanListHandler<ProductDB>(ProductDB.class));
        System.out.println("getProductLimit 中productLimitList"+productLimitList);
        return productLimitList;
    }

    @Override
    public boolean modifyProductInfoInDB(ProductDB productDB) throws SQLException {
        String url = productDB.getImgurl();
        if (url!=null){
            String sql="UPDATE product SET pname=?,estoreprice=?,markprice=?,pnum=?,cname=?,imgurl=? WHERE pid=?; ";
            int update = queryRunner.update(sql, productDB.getPname(), productDB.getEstoreprice(), productDB.getMarkprice(),
                    productDB.getPnum(), productDB.getCname(), productDB.getImgurl(), productDB.getPid());
            if (update==1){
                return true;
            }
        }else{
            String sql="UPDATE product SET pname=?,estoreprice=?,markprice=?,pnum=?,cname=? WHERE pid=?; ";
            int update = queryRunner.update(sql, productDB.getPname(), productDB.getEstoreprice(), productDB.getMarkprice(),
                    productDB.getPnum(), productDB.getCname(), productDB.getPid());
            if (update==1){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ProductDB> getAllProductByCname(String cname) throws SQLException {
        List<ProductDB> productDBList = queryRunner.query("select * from product where cname=?", cname,
                new BeanListHandler<ProductDB>(ProductDB.class));

        System.out.println("getAllProductByCname中"+productDBList);
        return productDBList;

    }

    @Override
    public ProductDB getOneProductByPidInDB(int id) throws SQLException {
        ProductDB productDB = queryRunner.query("select * from product where pid=?", id,
                new BeanHandler<ProductDB>(ProductDB.class));

        System.out.println("getOneProductByPidInDB中="+productDB);
        return productDB;



    }

    @Override
    public List<ProductDB> getAllProductsBySearchPname(String pname) throws SQLException {
        String sql = "select * from product ";
        List<ProductDB> queryList =null;
        if (!pname.isEmpty()){
            pname="%"+pname+"%";
            sql="SELECT * FROM product WHERE pname LIKE ? OR description LIKE ?;";
            queryList = queryRunner.query(sql, new Object[]{pname, pname}, new BeanListHandler<ProductDB>(ProductDB.class));
        }else {
            queryList = queryRunner.query(sql, new BeanListHandler<ProductDB>(ProductDB.class));
        }
        System.out.println("getAllProductsBySearchPname中的queryList="+queryList);
        return queryList;
    }
}
