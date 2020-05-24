package com.mjh.www.control.user;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Route;
import com.mjh.www.po.User;
import com.mjh.www.service.UserService;
import com.mjh.www.util.StartLineUtil;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户获取商品的详情
 */
@WebServlet("/detailServlet")
public class DetaliServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int id = Integer.parseInt(req.getParameter("id"));

        User user = (User)req.getSession().getAttribute("two");
        UserService userService = new UserService();
        Msg msg = userService.find(id);
        Route route = (Route)msg.getMessage();
        Msg isFav = userService.isFavourite(user,id);
        if("已经收藏".equals(isFav.getResult())){
            route.setFavourite(true);
        }else{
            route.setFavourite(false);
        }
        if("成功".equals(msg.getResult())){
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(route);
            resp.getWriter().write(json);
        }else{
            resp.getWriter().write("查询失败");
        }

    }
}
