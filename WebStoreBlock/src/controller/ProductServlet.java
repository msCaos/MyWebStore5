package controller;

import bean.Category;
import bean.Product;
import bean.ProductSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.CategoryService;
import service.ProductService;
import utils.PageHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {
    Product product = new Product();
    CategoryService categoryService = new CategoryService();
    ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        System.out.println("进ProductServlet时的top="+op);
        List<Category> categories = categoryService.getAllCategory();
        request.setAttribute("categories",categories);
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "addProduct":
                    addProduct(request,response);
                    break;
                case "findAllCategory":
                    findAllCategory(request,response);
                    break;
                case "findAllProduct":
                    findAllProduct(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "modifyProductInfo":
                    modifyProductInfo(request,response);
                    break;
                case "getProductByPid":
                    getProductByPidUpdate(request,response);
                    break;
                case "getAllCategory":
                    getAllCategory(request,response);
                    break;
                case "searchProductByCondition":
                    searchProductByCondition(request,response);
                    break;
            }
        }
    }


    private void searchProductByCondition(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String pid = null;
        String cid =null;
        String pname=null;
        String minprice =null;
        String maxprice =null;
        String num =null;
        ProductSession productsession = (ProductSession)session.getAttribute("sessionProduct");
        if (productsession!=null){

            pid = request.getParameter("pid");
            cid = request.getParameter("cid");
            pname = request.getParameter("pname");
            minprice = request.getParameter("minprice");
            maxprice = request.getParameter("maxprice");

            System.out.println("pid="+pid+"cid="+cid+"pname="+pname+"minprice="+minprice+"maxprice="+maxprice);

            if (pid!=null && !pid.isEmpty()){
                pid = request.getParameter("pid");
                productsession.setPid(pid);
            }else{
                pid = productsession.getPid();
            }
            num = request.getParameter("num");

            if (cid!=null && !cid.isEmpty()){
                cid = request.getParameter("cid");
                productsession.setCid(cid);
            }else{
                cid = productsession.getCid();
            }
            if (pname!=null && !pname.isEmpty()){
                pname = request.getParameter("pname");
                productsession.setPname(pname);
            }else{
                pname = productsession.getPname();
            }
            if (minprice!=null && !minprice.isEmpty()){
                minprice = request.getParameter("minprice");
                productsession.setMinprice(minprice);
            }else{
                minprice = productsession.getMinprice();
            }
            if (maxprice!=null && !maxprice.isEmpty()){
                maxprice = request.getParameter("maxprice");
                productsession.setMaxprice(maxprice);
            }else{
                maxprice = productsession.getMaxprice();
            }

        }else{
            productsession = new ProductSession();

            pid = request.getParameter("pid");
            cid = request.getParameter("cid");
            pname = request.getParameter("pname");
            minprice = request.getParameter("minprice");
            maxprice = request.getParameter("maxprice");
            num = request.getParameter("num");

            productsession.setCid(cid);
            productsession.setPid(pid);
            productsession.setMaxprice(maxprice);
            productsession.setMinprice(minprice);
            productsession.setPname(pname);

            session.setAttribute("sessionProduct",productsession);
        }

        try {
            PageHelper<Product> list = productService.getProductInCondition(pid,cid,pname,minprice,maxprice,num);
            if (list!=null){
                request.setAttribute("page",list);
                request.getRequestDispatcher("/admin/product/conditionSearchProductList.jsp").forward(request,response);
            }
            else{
                response.getWriter().println("查询结果为空或查询失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Category> list = categoryService.getAllCategory();
        if (list!=null){
            request.setAttribute("categories",list);
            request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request,response);
        }
        else{
            response.getWriter().println("列表为空或查询失败!");
        }

    }

    private void   getProductByPidUpdate(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        try {
            Product product = productService.selectProductByPid(pid);
            System.out.println("productUpdate="+product);
            if (product!=null){
                request.setAttribute("product",product);
                request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);
            }else{
                response.getWriter().println("product为null,编辑失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        boolean res = false;
        try {
            res = productService.deleteOneInfo(pid);
            if (res) {
                response.getWriter().println("删除成功！");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct");
            }else{
                response.getWriter().println("删除失败！");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        if (num==null || num.isEmpty()){
            num="1";
        }
        PageHelper<Product> list=null;
        try {
            list = productService.findProductListByNum(num);
            System.out.println("list1="+list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("list2="+list);
        if (list!=null){
            System.out.println("******************ProductServlet中所有产品分页*************"+list);
            request.setAttribute("page",list);
            request.getRequestDispatcher("/admin/product/productList.jsp").forward(request,response);
        }
        else{
            response.getWriter().println("列表为空或查询失败!");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> iterator =  fileItems.iterator();
            while(iterator.hasNext()){
                FileItem item = iterator.next();

                if (item.isFormField()){
                    processFormFild(item);
                }else{
                    processUpLoaded(item);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("product="+product);
        boolean res = productService.insertInfo(product);
        if (res){
            response.getWriter().println("插入成功！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct");
        }else{
            response.getWriter().println("插入失败！");
        }
    }

    private void processUpLoaded(FileItem item) {
        try {
            String fileName = item.getName();
            System.out.println("更换图片中的fileName="+fileName);//空
            //String fieldName = item.getFieldName();
            //System.out.println("更换图片中的fieldName="+fieldName);
            if (!fileName.isEmpty()){
                String[] split = fileName.split("/.");
                String name=null;
                if (split!=null) {
                    name = split[split.length - 1];
                }
                System.out.println("更换图片中的name="+name);
                String uuid = UUID.randomUUID().toString();
                String saveFile = uuid+"."+name;
                System.out.println("saveFile="+saveFile);
                String realPath = getServletContext().getRealPath("/files/"+saveFile);
                System.out.println("realPath="+realPath);
                String imgurl="/files/"+saveFile;
                product.setImgurl(imgurl);
                File file = new File(realPath);
                item.write(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processFormFild(FileItem item) throws UnsupportedEncodingException {
        String value = null;
        try {
            value = item.getString("UTF-8");
            System.out.println("value="+value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String fieldName = item.getFieldName();
        switch (fieldName){
            case "cid":
                product.setCid(value);
                break;
            case "pid":
                product.setPid(value);
                break;
            case "pnum":
                product.setPnum(value);
                break;
            case "pname":
                product.setPname(value);
                break;
            case "estoreprice":
                product.setEstoreprice(value);
                break;
            case "markprice":
                product.setMarkprice(value);
                break;
            case "description":
                product.setDescription(value);
                break;
        }
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = categoryService.getAllCategory();
        if (list!=null){
            request.setAttribute("category",list);
            request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request,response);
        }
        else{
            response.getWriter().println("列表为空或查询失败!");
        }
    }
    private void modifyProductInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> iterator =  fileItems.iterator();
            while(iterator.hasNext()){
                FileItem item = iterator.next();

                if (item.isFormField()){
                    processFormFild(item);
                }else{
                    processUpLoaded(item);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("获取到的保存信息对象里product="+product);
        System.out.println("图片的url"+product.getImgurl());
        boolean res = false;
        try {
            res = productService.modifyPdInfo(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res){
            response.getWriter().println("修改成功！");
           response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
        }else{
            response.getWriter().println("修改失败！");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
