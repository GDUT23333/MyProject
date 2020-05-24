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
 * 商家注册时检查手机是否被注册
 */
@WebServlet("/registerCheckPhone")
public class RegisterCheckPhone extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String phone = req.getParameter("phone");

        System.out.println(phone);
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.checkPhone(phone);
        resp.getWriter().write(msg.getResult());
    }
}
