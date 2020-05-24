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
 * 商家激活
 */
@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String code = req.getParameter("code");
        MarchantService marchantService = new MarchantService();
        Msg msg  = marchantService.active(code);
        String response;
        if("激活成功".equals(msg.getResult())){
            req.getRequestDispatcher("/view/register/registerActive.jsp").forward(req,resp);
            // response = "激活成功<a href='http://localhost:8080/view/login/login.jsp'>点击登录</a>";
        }else{
            response = "激活失败，请联系管理员";
        }
        //resp.getWriter().write(response);
    }
}
