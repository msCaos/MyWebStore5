package service;

import Dao.impls.OrderDaoImpls;
import Dao.impls.ProductDaoImpls;
import bean.OrderItems;
import bean.Orders;
import bean.User;
import utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    OrderDaoImpls orderDaoImpls = new OrderDaoImpls();
    CartService cartService =new CartService();
    UserService userService = new UserService();
    public boolean InsertOrderItems(String oid, String[] pids,int uid) throws SQLException {
        boolean flag = false;
        for (int i = 0; i <pids.length ; i++) {
            flag=false;
            int id =0;
            String pid = pids[i];
            try {
                id = Integer.parseInt(pid);

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            int cartid = cartService.getCartidByUid(uid);
            int num = cartService.getProNumByPidAndCartid(cartid,id);
            OrderItems orderItems = new OrderItems();
            orderItems.setOid(oid);
            orderItems.setPid(id);
            if (num!=0){
                orderItems.setBuynum(num);
            }else {
                System.out.println("*******************************这里商品数量为0********错误信息！*******************************");
            }
            boolean res = orderDaoImpls.insertItemInToDB(orderItems);
            if (res){
                flag=true;
            }else {
                System.out.println("****这是第"+i+"******次");
                break;
            }
        }

        return flag;
    }

    public boolean InsertOrderInOrders(Orders orders) throws SQLException {
        int update = orderDaoImpls.InsertOrderToDB(orders);
        if (update==1){
            return true;
        }
        return false;
    }

    public List<Orders> getAllOrdersInfo(int uid) throws SQLException {
        List<Orders> ordersList = orderDaoImpls.getAllMyOrdersInDB(uid);
        return ordersList;
    }

    public boolean deleteProductInCartByCartidAndPid(int uid, String[] pids) throws SQLException {
        boolean flag = false;
        for (int i = 0; i <pids.length ; i++) {
            flag=false;
            int id =0;
            String pid = pids[i];
            try {
                id = Integer.parseInt(pid);

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            int cartid = cartService.getCartidByUid(uid);
            boolean res = orderDaoImpls.deleteProductInCartByCartidAndPidInDB(cartid,pid);
            if (res){
                flag=true;
            }else {
                System.out.println("****这是第"+i+"******次删除*********************");
                break;
            }
        }

        return flag;
    }

    public boolean changeOrderState(String oid, int orderState) throws SQLException {
        boolean res = orderDaoImpls.changeOrderStateByOidInDB(oid,orderState);
        return res;
    }

    public PageHelper<Orders> getAllUsersOrdersInfo(String num) throws SQLException {
        int currentPageNum=0;
        try {
            currentPageNum = Integer.parseInt(num);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        List<Orders> allOrderList = orderDaoImpls.getAllUsersOrdersInfoInDB();
        int totalRecordsNum = allOrderList.size();
        int PAGE_COUNT=3;
        int limit = PAGE_COUNT;
        int offset=(currentPageNum-1)*PAGE_COUNT;
        List<Orders> ordersLimitList = orderDaoImpls.getOrdersLimit(limit, offset);
        for (Orders order:ordersLimitList) {
            int uid = order.getUid();
            User user = userService.getUserByUid(uid);
            order.setUser(user);
        }
        PageHelper<Orders> pageHelper = new PageHelper<>(currentPageNum, totalRecordsNum, PAGE_COUNT);
        pageHelper.setRecords(ordersLimitList);
        return pageHelper;
    }

    public  List<OrderItems> getOrderDetailsByOid(String oid) throws SQLException {
        List<OrderItems> itemsList= orderDaoImpls.getOrderDetailsByOidInDB(oid);
        return  itemsList;
    }

    public boolean deleteOrderByOid(String oid) throws SQLException {
        boolean res = orderDaoImpls.deleteOrderByOidInDB(oid);
        return res;
    }

    public boolean deleteOrderItemByOidAndPid(String oid, String pid) throws SQLException {
        int productId=0;
        try {
            productId = Integer.parseInt(pid);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        boolean res = orderDaoImpls.deleteOrderItemByPidAadOidInDB(oid,productId);
        return res;
    }
}
