package com.mjh.www.control.user;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Page;
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

/**
 * 检验商品是否被收藏，控制前端按钮显示
 */
@WebServlet("/checkMyFavouriteServlet")
public class CheckMyFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User)req.getSession().getAttribute("two");
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int startLine = StartLineUtil.getStartLine(currentPage,pageSize);
        UserService userService = new UserService();
        Msg msg = userService.countFavourite(user);
        int totalCount = (int)msg.getMessage();
        if(totalCount == 0){
            resp.getWriter().write("还未有收藏");
            return;
        }
        int totalPage = PageUtil.getTotalPage(pageSize,totalCount);

        Page page  = (Page)userService.checkMyFavourite(user,startLine,pageSize,currentPage,totalCount,totalPage).getMessage();
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(page);
        resp.getWriter().write(data);
    }
}
