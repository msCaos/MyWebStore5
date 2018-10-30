package controller;

import bean.User;
import service.ProductService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AjaxServlet",urlPatterns = "/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op != null && !op.isEmpty()) {
            switch (op) {
                case "isUserUsernameAvailable":
                    isUserUsernameAvailable(request, response);
                    break;
            }
        }
    }

    private void isUserUsernameAvailable(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        try {
            User user = userService.getUserByUsername(username);
            if (user!=null){
                response.getWriter().print("false");
            }else {
                response.getWriter().print("true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
