package service;

import Dao.impls.CategoyrDaoImpls;
import Dao.impls.ProductDaoImpls;
import bean.Category;
import bean.Product;
import bean.ProductDB;
import utils.PageHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    ProductDaoImpls productDaoImpls = new ProductDaoImpls();
    CategoyrDaoImpls categoyrDaoImpls = new CategoyrDaoImpls();

    public ProductDB changeProductToProductDB(Product product){

        ProductDB productDB = new ProductDB();
        try {
            int pid = Integer.parseInt(product.getPid());
            productDB.setPid(pid);

            productDB.setPname(product.getPname());

            float estoreprice = Float.parseFloat(product.getEstoreprice());
            productDB.setEstoreprice(estoreprice);

            float markprice = Float.parseFloat(product.getMarkprice());
            productDB.setMarkprice(markprice);

            int pnum = Integer.parseInt(product.getPnum());
            productDB.setPnum(pnum);

            productDB.setImgurl(product.getImgurl());

            productDB.setDescription(product.getDescription());

            int cid = Integer.parseInt(product.getCid());
            System.out.println("cid="+cid);
            String cname = categoyrDaoImpls.getCnameByCid(cid);
            System.out.println("cname="+cname);

            productDB.setCname(cname);
            return productDB;

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        System.out.println("productDB1="+productDB);
        return null;
    }
    public Product changeProductDBToProduct(ProductDB productDB) throws SQLException {
        Product product = new Product();

        String pid = String.valueOf(productDB.getPid());
        product.setPid(pid);

        product.setPname(productDB.getPname());

        String estoreprice = String.valueOf(productDB.getEstoreprice());
        product.setEstoreprice(estoreprice);

        String markprice = String.valueOf(productDB.getMarkprice());
        product.setMarkprice(markprice);

        String pnum = String.valueOf(productDB.getPnum());
        product.setPnum(pnum);

        product.setCname(productDB.getCname());

        product.setImgurl(productDB.getImgurl());

        product.setDescription(productDB.getDescription());

        String cid = categoyrDaoImpls.getCidByCname(productDB.getCname());
        product.setCid(cid);

        List<Category> categories = categoyrDaoImpls.getAllCategoryInDB();
        product.setCategory(categories);
        return product;
    }

    public boolean insertInfo(Product product){
        ProductDB productDB = this.changeProductToProductDB(product);
        System.out.println("productDB2="+productDB);
        boolean res = productDaoImpls.insertInfoInToDB(productDB);
        return res;
    }

    public List<ProductDB> findAllInfo() throws SQLException {
        List<ProductDB> list = productDaoImpls.findAllInfoInDB();
        return list;
    }

    public PageHelper<Product> findProductListByNum(String num) throws SQLException {
        int currentPageNum =0;
        try {
            currentPageNum = Integer.parseInt(num);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        List<ProductDB> listall = productDaoImpls.findAllInfoInDB();
        System.out.println("listall="+listall);
        int totalRecordsNum =listall.size();
        int PAGE_COUNT=3;
        PageHelper<Product> pageHelper = new PageHelper<Product>(currentPageNum,totalRecordsNum,PAGE_COUNT);

        //limit为每一页显示的条数，offset为从哪个元素开始起始，表示查找从offset起始的limit个元素
        int limit = PAGE_COUNT;
        int offset=(currentPageNum-1)*PAGE_COUNT;

        //查询出错！
        List<ProductDB> partProductInDB = productDaoImpls.getPartProductInDB(limit, offset);

        System.out.println("findProductListByNum中的partProduInService="+partProductInDB);

        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:partProductInDB) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        System.out.println("products="+products);
        pageHelper.setRecords(products);
        return pageHelper;
    }


    public boolean deleteOneInfo(String pid) throws SQLException {
        boolean res =false;
        try {
            int id = Integer.parseInt(pid);
            res = productDaoImpls.deleteOneInfoInDB(id);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return res;
    }

    public Product selectProductByPid(String pid) throws SQLException {
        try {
            int id = Integer.parseInt(pid);
            ProductDB productDB = productDaoImpls.selectProductByPidInDB(id);
            Product product = this.changeProductDBToProduct(productDB);
            return product;
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
       return null;
    }

    public PageHelper<Product> getProductInCondition(String pid, String cid, String pname, String minprice, String maxprice, String num) throws SQLException {
        int id =0;
        int currentPageNum=1;
        String cname=null;
        try {
            if (cid!=null && !cid.isEmpty()){
            id = Integer.parseInt(cid);
            cname = categoyrDaoImpls.getCnameByCid(id);
            }
            currentPageNum = Integer.parseInt(num);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        System.out.println("cname="+cname);
        List<ProductDB> list = productDaoImpls.getProductInConditionInDB(pid,cname,pname,minprice,maxprice);

        int PAGE_COUNT=3;
        int limit = PAGE_COUNT;
        int offset=(currentPageNum-1)*PAGE_COUNT;
        int totalRecordsNum = list.size();
        List<ProductDB> productLimitList = productDaoImpls.getProductLimit(pid, cname, pname, minprice, maxprice, limit, offset);

        PageHelper<Product> pageHelper = new PageHelper<>(currentPageNum, totalRecordsNum, PAGE_COUNT);
        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:productLimitList) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        pageHelper.setRecords(products);
        return pageHelper;

    }

    public boolean modifyPdInfo(Product product) throws SQLException {
        ProductDB productDB = this.changeProductToProductDB(product);
        System.out.println("modifyPdInfo中的productDB="+productDB);
        System.out.println("modifyPdInfo中的imgurl="+productDB.getImgurl());
        boolean result = productDaoImpls.modifyProductInfoInDB(productDB);
        return result;
    }

    public List<Product> getProductTop(int limit, int offset) throws SQLException {
        List<ProductDB> partProductInDB = productDaoImpls.getPartProductInDB(limit, offset);
        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:partProductInDB) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        return products;
    }

    public List<Product> getHotProducts() throws SQLException {
        List<ProductDB> allInfoInDB = productDaoImpls.findAllInfoInDB();
        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:allInfoInDB) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        return products;
    }

    public List selectAllProductByCid(String cid) throws SQLException {
        int id =0;
        try {
            id = Integer.parseInt(cid);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        String cname = categoyrDaoImpls.getCnameByCid(id);
        List<ProductDB> productDBS = productDaoImpls.getAllProductByCname(cname);
        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:productDBS) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        return products;
    }

    public Product selectOneProductByPid(String pid) throws SQLException {
        int id =0;
        try {
            id = Integer.parseInt(pid);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        ProductDB productByPid = productDaoImpls.getOneProductByPidInDB(id);
        Product product = this.changeProductDBToProduct(productByPid);
        return product;
    }

    public List<Product> getAllProductsExistPname(String pname) throws SQLException {
        List<ProductDB> productDBS = productDaoImpls.getAllProductsBySearchPname(pname);
        List<Product> products = new ArrayList<>();
        for (ProductDB productdb:productDBS) {
            Product product = this.changeProductDBToProduct(productdb);
            products.add(product);
        }
        return products;
    }
}
