package com.mjh.www.control.page;

import com.mjh.www.po.Page;
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
 * 商家端的商品上架页面
 */
@WebServlet("/merchantFindAllServlet")
public class FindAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String email = req.getParameter("email");
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));


        PageService pageService = new PageService();
        int totalCount = (int)pageService.merchantCount(email).getMessage();
        int totalPage = PageUtil.getTotalPage(pageSize,totalCount);
        if(totalCount == 0){
            resp.getWriter().write("还未上架任何商品");
            return;
        }
        if(currentPage >= totalPage){
            currentPage = totalPage;
        }
        Page page = new Page();
        int startLine = StartLineUtil.getStartLine(currentPage,pageSize);

        page = (Page) pageService.findAll(email,currentPage,pageSize,startLine,totalPage,totalCount).getMessage();


        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(page);
        resp.getWriter().write(data);
    }
}
