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
 * 用户注册时检验是否已经注册
 */
@WebServlet("/checkUserServlet")
public class CheckUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        UserService userService = new UserService();
        Msg msg = userService.isRegister(email,phone);
        resp.getWriter().write(msg.getResult());
        System.out.println(msg.getResult());
    }
}
