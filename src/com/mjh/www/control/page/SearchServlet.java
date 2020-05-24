package com.mjh.www.control.page;

import com.mjh.www.po.Page;
import com.mjh.www.po.Route;
import com.mjh.www.po.User;
import com.mjh.www.service.UserService;
import com.mjh.www.util.PageUtil;
import com.mjh.www.util.StartLineUtil;
import org.codehaus.jackson.map.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 商品的模糊查询
 */
@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        UserService userService = new UserService();
        int id = Integer.parseInt(req.getParameter("id"));
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        boolean islogin = false;
        String inf = req.getParameter("inf");
        System.out.println(inf);
        int startLine = StartLineUtil.getStartLine(currentPage,pageSize);
        int totalCount = (Integer) userService.totalCount(id,inf).getMessage();
        if(totalCount == 0){
            resp.getWriter().write("搜索不到商品");
            return;
        }
        int totalPage = PageUtil.getTotalPage(pageSize,totalCount);
        Page page = new Page(id,pageSize,startLine,totalPage,inf);
        List<Route> list = (List<Route>) userService.search(page).getMessage();
        User user = (User)req.getSession().getAttribute("two");
        if(user != null){
            islogin = true;
        }
        page.setCurrentPage(currentPage);
        page.setList(list);
        page.setTotalCount(totalCount);
        page.setLogin(islogin);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(page);
        resp.getWriter().write(json);
    }
}
