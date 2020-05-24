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
 * 删除上架商品
 */
@WebServlet("/deleteCommodityServlet")
public class DeleteCommodityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        MarchantService  marchantService = new MarchantService();
        Msg msg = marchantService.deleteCommodity(id);
        resp.getWriter().write(msg.getResult());
    }
}
