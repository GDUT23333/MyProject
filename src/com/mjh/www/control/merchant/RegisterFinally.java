package com.mjh.www.control.merchant;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Mail;
import com.mjh.www.po.MerChant;
import com.mjh.www.service.MarchantService;
import com.mjh.www.util.CodeUtil;
import com.mjh.www.util.MailUtil;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 注册最后步骤，并发送激活邮件
 */
@WebServlet("/registerFinally")
public class RegisterFinally extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String data = req.getParameter("data");
        System.out.println(data);
        ObjectMapper mapper = new ObjectMapper();
        MerChant one = mapper.readValue(data,MerChant.class);
        System.out.println(one.getName());

        //设置激活码，唯一字符串
        String code = CodeUtil.getCode();


        String email = one.getEmail();
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.regist(code,one);
        if("注册成功".equals(msg.getResult())){
            //记录注册用户数据
            HttpSession session = req.getSession();
            session.setAttribute("merchant",one);
            //发送激活邮件
            String content = "<a href='http://localhost:8080/activeServlet?code="+code+"'>点击激活</a>";
            Mail mail = new Mail(email,content,"激活邮件");
            MailUtil.sendMail(mail);
            req.getRequestDispatcher("view/register/registerThree.jsp").forward(req,resp);
        }
    }
}
