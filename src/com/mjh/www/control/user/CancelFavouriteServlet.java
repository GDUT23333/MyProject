package com.mjh.www.control.user;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.User;
import com.mjh.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 取消收藏
 */
@WebServlet("/cancelFavouriteServlet")
public class CancelFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User)req.getSession().getAttribute("two");
        int routeId = Integer.parseInt(req.getParameter("routeId"));
        UserService userService = new UserService();
        Msg msg = userService.cancelFavourite(user,routeId);
        resp.getWriter().write(msg.getResult());
    }
}
