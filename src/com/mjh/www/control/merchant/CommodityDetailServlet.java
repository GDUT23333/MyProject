package com.mjh.www.control.merchant;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Route;
import com.mjh.www.service.MarchantService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 商家得到自己上架商品详情的Servlet
 */
@WebServlet("/merchantDetailServlet")
public class CommodityDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.checkDetail(id);
        Route route = (Route)msg.getMessage();
        if("成功".equals(msg.getResult())){

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(route);
            resp.getWriter().write(json);
        }else{
            resp.getWriter().write("查询失败");
        }

    }
}
