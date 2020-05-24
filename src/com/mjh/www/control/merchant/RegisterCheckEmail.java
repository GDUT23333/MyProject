package com.mjh.www.control.merchant;

import com.mjh.www.bean.Msg;
import com.mjh.www.service.MarchantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 商家注册时检验邮箱是否被注册
 */
@WebServlet("/registerCheckEmail")
public class RegisterCheckEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.checkEmail(email);
        resp.setContentType("text/html;charset=utf-8");
        String message = msg.getResult();
        System.out.println(message);
        resp.getWriter().write(msg.getResult());
    }
}
