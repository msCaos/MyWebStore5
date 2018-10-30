package controller;

import bean.*;
import service.CartService;
import service.CategoryService;
import service.ProductService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {
    CategoryService categoryService = new CategoryService();
    ProductService productService = new ProductService();
    CartService cartService = new CartService();
    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        List<Category> categories = categoryService.getAllCategory();
        request.setAttribute("categories", categories);
        String op = request.getParameter("op");
        System.out.println("******************这里是CartServlet **op="+op);
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "findCart":
                    findCart(request,response);
                    break;
                case "addCart":
                    addProductToCart(request,response);
                    break;
                case "delItem":
                    delItem(request,response);
                    break;
            }
        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String itemid = request.getParameter("itemid");
        try {
            int userId = Integer.parseInt(uid);
            int itemId = Integer.parseInt(itemid);
            int cartid = cartService.getCartidByUid(userId);
            //删除成功后重定向到去购物车获取所有信息
            boolean res = cartService.deleteProductsInCartByItemidAndCartid(itemId,cartid);
            if (res){
                response.sendRedirect("/CartServlet?op=findCart");
            }

        }catch (NumberFormatException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProductToCart(HttpServletRequest request, HttpServletResponse response) {
        /**注意：页面从products中添加购物车不会携带snum参数 购物车默认商品添加一
         * 从detail页面进去会携带参数snum,snum不会为null
         * 添加的思路：当要添加一个商品的时候，根据uid去寻找购物车cartid，若cartid不存在，创建对应的cartid
         *
         * 再获取对应的cartid后去item中添加对应的信息
         * 若cartid存在，拿到cartid后在item中寻找对应的pid是否存在，若pid存在，则购物车pid对应的snum+1
         * 若pid不存在，存储pid和sunm信息（此时产生一个新的itemid)
         * update item  set sum =sum+ ? where cartid= ? and pid =? 如果为false 则说明这条信息不存在，
         * 重新插入并返回一个对象
         *参数的回显:
         */
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        String snum = request.getParameter("snum");
        System.out.println("**********snum="+snum);
        if (snum==null || snum.isEmpty()){
            snum="1";
        }
        try {
            int num = Integer.parseInt(snum);
            int productId = Integer.parseInt(pid);
            int userId = Integer.parseInt(uid);
            int cartid = cartService.getCartidByUid(userId);
            //获取到一个cartId
            if (cartid!=0){
                //Item item = new Item();
                Item  item =cartService.InsertProductToCart(cartid,productId,num);
                if (item!=null){
                    //插入成功后应该在重定向到获取当前购物车
                    response.sendRedirect("/CartServlet?op=findCart");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NumberFormatException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("****************刚登陆进购物车时********user"+user);
        int uid = user.getUid();
        int cartid = 0;
        try {
            cartid = cartService.getCartidByUid(uid);
            List<Item> shopCartList = cartService.getAllProductsInCartBy(cartid);
            System.out.println("+++++++++++++++++++++++++++刚登陆进购物车时所有商品++++++++++++++++++++++++++"+shopCartList);
            if (shopCartList!=null){
                ShopCart shopCart = new ShopCart();
                shopCart.setItemList(shopCartList);
                shopCart.setCartid(cartid);
                shopCart.setUid(uid);
                session.setAttribute("shoppingCar",shopCart);
                request.getRequestDispatcher("/shoppingcart.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
