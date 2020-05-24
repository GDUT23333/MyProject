package com.mjh.www.control.merchant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册时，邮箱未被注册时就经过该Servlet跳转到第二个注册页面，并进行邮箱回显
 */
@WebServlet("/registerSendEmail")
public class RegisterSendEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String email = req.getParameter("email");
        req.setAttribute("email",email);
        System.out.println(email);
        req.getRequestDispatcher("view/register/registerTwo.jsp").forward(req,resp);
    }
}
