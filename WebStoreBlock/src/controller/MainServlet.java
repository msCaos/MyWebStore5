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

@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    CategoryService categoryService = new CategoryService();
    ProductService productService = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        List<Category> categories = categoryService.getAllCategory();
        request.setAttribute("categories", categories);
        String op = request.getParameter("op");
        System.out.println("******************这里是MainServlet **op="+op);
        if ("personInfo".equals(op)){
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
        }else if ("myOrder".equals(op)){
            request.getRequestDispatcher("/placeOrder.jsp").forward(request, response);
        }
        else {
            int limit = 5;
            int offset = 1;
            try {
                List<Product> productTop = productService.getProductTop(limit, offset);
                request.setAttribute("productTop", productTop);
                List<Product> hotProducts = productService.getHotProducts();
                request.setAttribute("hotProducts", hotProducts);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
