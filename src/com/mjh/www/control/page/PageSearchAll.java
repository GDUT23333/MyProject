package com.mjh.www.control.page;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Category;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.Page;
import com.mjh.www.po.User;
import com.mjh.www.service.PageService;
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
 * 首页的展示商品
 */
@WebServlet("/pageSearchAll")
public class PageSearchAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PageService pageService = new PageService();
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        String inf = req.getParameter("inf");
        int totalCount = (int)pageService.findAllTotalCount(inf).getMessage();
        System.out.println(inf);
        System.out.println(totalCount);

        int totalPage = PageUtil.getTotalPage(pageSize,totalCount);
        int startLines = StartLineUtil.getStartLine(currentPage,pageSize);

        boolean islogin = false;
        User user = (User)req.getSession().getAttribute("two");
        if(user != null){
            islogin = true;
        }
        Page page = (Page) pageService.searchAll(startLines,totalPage,pageSize,currentPage,totalCount,inf).getMessage();
        page.setLogin(islogin);
        ObjectMapper obj = new ObjectMapper();
        String json = obj.writeValueAsString(page);

        resp.getWriter().write(json);
    }
}
