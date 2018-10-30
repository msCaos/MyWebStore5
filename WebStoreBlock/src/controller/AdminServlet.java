package controller;

import bean.Admin;
import service.AdminService;
import utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminServlet" ,urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
    AdminService adminService = new AdminService();
    Admin admin = new Admin();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op != null && !op.isEmpty()) {
            switch (op) {
                case "addAdmin":
                    addAdmin(request, response);
                    break;
                case "findAllAdmin":
                    findAllAdmin(request,response);
                    break;
                case "updateAdmin":
                    updateAdmin(request,response);
                    break;
                case "login":
                    adminLogin(request,response);
                    break;
                case "logout":
                    adminLogout(request,response);
                    break;
            }

        }
    }

    private void adminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("admin")!=null){
            session.invalidate();
            response.getWriter().println("註銷成功！");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/index.jsp");
        }
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (password != null && !password.isEmpty() && username != null && !username.isEmpty() ){
            try {
                boolean res = adminService.matchAdminUsernameAndPassword(username,password);
                if (res){
                    HttpSession session = request.getSession();
                    admin.setUsername(username);
                    admin.setPassword(password);
                    session.setAttribute("admin",admin);
                    response.getWriter().println("登录成功！即将登录主页！");
                    response.setHeader("refresh", "1;url=" + request.getContextPath() + "/admin/main.jsp");
                }else{
                    response.getWriter().println("账号密码不匹配！即将返回登录首页！");
                    response.setHeader("refresh", "3;url=" + request.getContextPath() + "/admin/index.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            response.getWriter().println("账号密码不能为空！即将返回登录首页！");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/index.jsp");

        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        System.out.println("servlet中获取到的id="+id);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if (password != null && !password.isEmpty() && password1 != null && !password1.isEmpty() ){
            if (password.equals(password1)){
                try {
                    boolean res = adminService.modifyAdminPassword(username,password);
                    if (res){
                        response.getWriter().println("管理員密碼修改成功！");
                        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }else {
                response.getWriter().println("两次密码不匹配！");
            }

        }else {
            response.getWriter().println("密码不能为空！");
        }

    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("num");
        if (num==null || num.isEmpty()){
            num="1";
        }
        try {
            PageHelper<Admin> list= adminService.findAllAdminByNum(num);
            System.out.println("servlet中findAllAdmin，list="+list);
            if (list!=null){
                request.setAttribute("page",list);
                request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request,response);
            }
            else{
                response.getWriter().println("列表为空或查询失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && password1 != null && !password1.isEmpty()) {

            if (password.equals(password1)) {
                //用户名查重
                //写入数据库
                boolean res = false;
                try {
                    res = adminService.checkRegisterName(username);
                    if (res) {
                        response.getWriter().println("该用户名已存在！");
                    } else {
                        boolean result = adminService.registerData(username, password);
                        if (result) {
                            response.getWriter().println("注册成功！");
                        } else {
                            response.getWriter().println("数据注册失败！");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                response.getWriter().println("密码不匹配！请重新填写密码！");
                response.setHeader("refresh","2;url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
            }
        } else {
            response.getWriter().println("注册信息不能为空！");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

