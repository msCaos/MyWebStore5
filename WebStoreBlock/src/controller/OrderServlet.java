package controller;

import bean.*;
import service.CategoryService;
import service.OrderService;
import utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends HttpServlet {
    CategoryService categoryService = new CategoryService();
    OrderService orderService = new OrderService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        List<Category> categories = categoryService.getAllCategory();
        request.setAttribute("categories", categories);
        String op = request.getParameter("op");
        System.out.println("******************这里是OrderServlet***op="+op);
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "myoid":
                    getMyAllOids(request,response);
                    break;
                case "placeOrder":
                    submitOrder(request,response);
                    break;
                case "cancelOrder":
                    cancelMyOrder(request,response);
                    break;
                case "findAllOrder":
                    adminfindAllOrders(request,response);
                    break;
                case "orderDetail":
                    getOneOrderDetailByOid(request,response);
                    break;
                case "deleteItem":
                    deleteThisItem(request,response);
                    break;
                case "delOrder":
                    delOrderByOid(request,response);
                    break;
            }
        }
    }

    private void delOrderByOid(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            boolean res = orderService.deleteOrderByOid(oid);
            if (res){
                request.getRequestDispatcher("/OrderServlet?op=findAllOrder&num=1").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteThisItem(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        String pid = request.getParameter("pid");
        try {
            boolean res = orderService.deleteOrderItemByOidAndPid(oid,pid);
            if (res){
                request.getRequestDispatcher("/OrderServlet?op=findAllOrder&num=1").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getOneOrderDetailByOid(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            List<OrderItems> orderItemsList = orderService.getOrderDetailsByOid(oid);
            if (orderItemsList!=null){
                request.setAttribute("orderitems",orderItemsList);
                request.getRequestDispatcher("/admin/order/orderDetails.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adminfindAllOrders(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("num");
        try {
            PageHelper<Orders> pageOrdersList = orderService.getAllUsersOrdersInfo(num);
            System.out.println("******************OrderServlet中所有订单分页*************"+pageOrdersList);
            request.setAttribute("page",pageOrdersList);
            request.getRequestDispatcher("/admin/order/orderList.jsp").forward(request,response);
//            response.sendRedirect("/admin/order/orderList.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void cancelMyOrder(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
        int orderState =0;
        try {
            orderState = Integer.parseInt(state);
            boolean res = orderService.changeOrderState(oid,orderState);
            if (res){
                response.sendRedirect("/OrderServlet?op=myoid");
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitOrder(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("uid");
        /**
         * buynum要去购物车的数据库根据商品的查找，如果从接受处理下单的srvlet获取到了某个商品的pid
         *  则这个商品要被在购物车中删除，则可以通过查找此商品在购物车数据库中的pid来获取num的值
         *  提交订单负责把数据放到订单的数据库，回显由查询数据库获取数据的方法来完成来完成
         *  取消订单只是订单状态的改变。这里前端客户没有权限删除订单，但是后台的管理员可以删除一个订单以及对应的所有
         *  订单里商品信息。同时注意在下单之后数据库里对应的商品库存数量要减少
         */

        String recipients = request.getParameter("recipients");
        String tel = request.getParameter("tel");
        String address = request.getParameter("address");

        //订单总金额
        String orderTotalMoney = request.getParameter("money");
        float money =0;
        int uid = 0;
        try {
            money = Float.parseFloat(orderTotalMoney);
            uid = Integer.parseInt(userid);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        //获取多个同名的pid
        String[] pids = request.getParameterValues("pid");
        System.out.println("***********pids的长度************"+pids.length);
        //获取订单编号
        String oid = GenerateNum.getInstance().GenerateOrder();
        //获取订单时间
        String ordertime = this.getNowDateStr();
        System.out.println("************订单编号="+oid+"****************订单时间="+ordertime);
        int ORDER_STATE=1;

        Orders orders = new Orders();
        orders.setOid(oid);
        orders.setMoney(money);
        orders.setRecipients(recipients);
        orders.setTel(tel);
        orders.setAddress(address);
        orders.setState(ORDER_STATE);
        orders.setOrdertime(ordertime);
        orders.setUid(uid);
        try {
            boolean res1 = orderService.InsertOrderInOrders(orders);
            //传参数让service去封装.
            boolean res2 = orderService.InsertOrderItems(oid,pids,uid);
            if (res1 && res2){
                //如果都插入成功，则将购物车选中下订单的产品删掉清空
                boolean res3 = orderService.deleteProductInCartByCartidAndPid(uid,pids);
                if (res3){
                    response.sendRedirect("/OrderServlet?op=myoid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMyAllOids(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int uid = user.getUid();
        try {
            List<Orders> myOrdersList = orderService.getAllOrdersInfo(uid);
            if (myOrdersList!=null){
                session.setAttribute("orders",myOrdersList);
            }
            request.getRequestDispatcher("/myOrders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // 获取当前时间年月日时分秒毫秒字符串
    private static String getNowDateStr() {
        return sdf.format(new Date());
    }

}
