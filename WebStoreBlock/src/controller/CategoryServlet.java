package controller;

import bean.Category;
import service.CategoryService;
import utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    CategoryService categoryService = new CategoryService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "addCategory":
                    addCategory(request,response);
                    break;
                case "findAllCategory":
                    findAllCategory(request,response);
                case "deleteMulti":
                    break;
                case "updateCategory":
                    updateCategory(request,response);
                    break;
                case "deleteThisInfo":
                    deleteThisInfo(request,response);
            }
        }
    }

    private void deleteThisInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cname = request.getParameter("cname");
        boolean res = categoryService.deleteTheInfoIn(cname);
        if (res){
            response.getWriter().println("删除成功！");
            response.setHeader("refresh","3;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
        }else {
            response.getWriter().println("删除失败！");
            response.setHeader("refresh","3;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        if (cname!=""){
            boolean res = categoryService.modifyCategory(cid, cname);
            if (res){
                response.getWriter().println("修改成功！");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
            }
        }else {
            response.getWriter().println("修改失败！分类类名不能为空！");
            response.setHeader("refresh","2;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
        }
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        if (num==null || num.isEmpty()){
            num="1";
        }
        PageHelper<Category> list = categoryService.findCategoryListByNum(num);
        System.out.println("list="+list);
        if (list!=null){
            request.setAttribute("page",list);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);
        }
        else{
            response.getWriter().println("列表为空或查询失败!");
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cname = request.getParameter("cname");
        int res = categoryService.addCategoryInfo(cname);
        if (res==1) {
            response.getWriter().println("添加成功！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory");
        }else if (res==-2){
            response.getWriter().println("数据添加失败！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/category/addCategory.jsp");
        }else if (res==-1){
            response.getWriter().println("该分类已存在！请重新填写分类！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/category/addCategory.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
