package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "WebStoreFilter",urlPatterns ="/admin/*" )
public class WebStoreFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String uri = request.getRequestURI();
        String op = request.getParameter("op");
        chain.doFilter(req, resp);
        System.out.println("********************************这些uri是*************************"+uri);
        if ( "/admin/index.jsp".equals(uri) ||("/admin/AdminServlet".equals(uri) && "login".equals(op))||"/admin/css/style.css".equals(uri)
                ||"/admin/js/js.js".equals(uri) ||"/admin/images/dl.gif".equals(uri) ||"/admin/images/cz.gif".equals(uri)){
            chain.doFilter(req, resp);
        }else{
            HttpSession session = request.getSession();
            if (session.getAttribute("admin")==null){
            response.getWriter().println("您还未登录，请先登录！");
            response.setHeader("refresh","2;url=/admin/index.jsp");
            }else{
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
