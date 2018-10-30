package controller;

import bean.Category;
import bean.Product;
import service.CategoryService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductDisplayServlet",urlPatterns = "/ProductDisplayServlet")
public class ProductDisplayServlet extends HttpServlet {
    Product product = new Product();
    CategoryService categoryService = new CategoryService();
    ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        System.out.println("进ProductDisplayServlet时的top="+op);
        List<Category> categories = categoryService.getAllCategory();
        request.setAttribute("categories",categories);
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "byCid":
                    customerSearchByCid(request,response);
                    break;
                case "findProductById":
                    findProductByPid(request,response);
                    break;
                case "findProByName":
                    findProBySearchName(request,response);
                    break;
            }
    }
    }

    private void customerSearchByCid(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        try {
            List<Product> product = productService.selectAllProductByCid(cid);
            request.setAttribute("product",product);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        try {
            Product product = productService.selectOneProductByPid(pid);
            request.setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findProBySearchName(HttpServletRequest request, HttpServletResponse response) {
        String pname = request.getParameter("pname");
        try {
            List<Product> product = productService.getAllProductsExistPname(pname);
            request.setAttribute("product",product);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
