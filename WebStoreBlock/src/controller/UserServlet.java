package controller;

import bean.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        System.out.println("进UserServlet时的top="+op);
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "regist":
                    registerUserInfo(request,response);
                    break;
                case "login":
                    userLogin(request,response);
                    break;
                case "lgout":
                    userLogOut(request,response);
                    break;
                case "updateUserInfo":
                    updateUserInfo(request,response);
                    break;
            }
        }
    }

    private void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        User user = new User();
        int id =0;
        if (uid!=null && !uid.isEmpty()){
            try {
                id = Integer.parseInt(uid);
                user.setUid(id);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setBirthday(birthday);
        try {
            boolean res = userService.modifyUserInfo(user);
            if (res){
                User user1 = userService.getUserByUid(id);
                if (user1!=null){
                    HttpSession session = request.getSession();
                    session.removeAttribute("user");
                    session.setAttribute("user",user);
                    response.getWriter().println("修改成功！");
                }
                response.setHeader("refresh","2;url="+request.getContextPath()+"/MainServlet?op=personInfo");
            }else {
                response.getWriter().println("修改失败！");
                response.setHeader("refresh","2;url="+request.getContextPath()+"/MainServlet?op=personInfo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void userLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        if (user!=null){
            session.removeAttribute("user");
        }
        response.getWriter().println("成功退出！");
        response.setHeader("refresh","1;url="+request.getContextPath()+"/MainServlet");
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        String verifyCode1 = (String)request.getSession(false).getAttribute("checkcode_session");
        User user =new User();
        user.setUsername(username);
        user.setPassword(password);
        String rememberPassword = request.getParameter("remember_me");
        System.out.println("rememberPassword="+rememberPassword);
        if (verifyCode.equals(verifyCode1)){
            try {
                User thisUser = userService.getUserByUsernameAndPassword(user);
                System.out.println("**********************************************="+thisUser);
                if (thisUser!=null){
                    HttpSession session = request.getSession();
                    if ("on".equals(rememberPassword)){
                        User userForLogin = (User)session.getAttribute("userForLogin");
                        System.out.println("userForLogin="+userForLogin);
                        if (userForLogin==null){
                            session.setAttribute("userForLogin",thisUser);
                        }
                    }else {
                        session.removeAttribute("userForLogin");
                    }
                    session.setAttribute("user",thisUser);
                    response.sendRedirect("/MainServlet");
//                response.getWriter().println("登录成功！");
//                response.setHeader("refresh","2;url="+request.getContextPath()+"/MainServlet");
                }else {
                    response.getWriter().println("登录失败！请重新输入用户名和密码！");
                    response.setHeader("refresh","2;url="+request.getContextPath()+"/user/login.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            response.getWriter().println("验证码不匹配！登录失败");
            response.setHeader("refresh","2;url="+request.getContextPath()+"/user/login.jsp");
        }


    }

    private void registerUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Map<String, String[]> map = request.getParameterMap();
//        for (Map.Entry<String,String[]> maps:map.entrySet()) {
//            String key = maps.getKey();
//            String[] value = maps.getValue();
//            System.out.println("key="+key+"      value="+value[0]);
//        }
//        System.out.println("registerUserInfo中的map="+map);
//        response.getWriter().println("注册成功！");
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String birthday = request.getParameter("birthday");
        if (password.equals(password1)){
            User user = new User();
            user.setUsername(username);
            user.setNickname(nickname);
            user.setEmail(email);
            user.setPassword(password);
            user.setBirthday(birthday);
            boolean res = false;
            try {
                res = userService.registerUserInfo(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (res){
                response.getWriter().println("注册成功！请在主页登录！");
                response.setHeader("refresh","2;url="+request.getContextPath()+"/index.jsp");
            }else {
                response.getWriter().println("数据库注册失败！请重新注册！");
                response.setHeader("refresh","2;url="+request.getContextPath()+"/user/regist.jsp");
            }
        }else {
            response.getWriter().println("密码不匹配！请重新输入!");
            response.setHeader("refresh","2;url="+request.getContextPath()+"/user/regist.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
