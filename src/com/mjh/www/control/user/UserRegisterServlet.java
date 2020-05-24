package com.mjh.www.control.user;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Mail;
import com.mjh.www.po.User;
import com.mjh.www.service.UserService;
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
 * 注册用户
 */
@WebServlet("/userRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String data = req.getParameter("data");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(data,User.class);
        String email = user.getEmail();
        String code = CodeUtil.getCode();
        String headPitcher = "head1.png";
        float money = 1000;
        user.setCode(code);
        user.setSign("");
        user.setStatus("N");
        user.setHeadPitcher(headPitcher);
        user.setMoney(money);
        UserService userService = new UserService();
        Msg msg = userService.register(user);
        if("注册成功".equals(msg.getResult())){
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            //发送激活邮件
            String content = "<a href='http://localhost:8080/userActiveServlet?code="+code+"'>点击激活</a>";
            Mail mail = new Mail(email,content,"激活邮件");
            MailUtil.sendMail(mail);
            req.getRequestDispatcher("/view/register/registerThree.jsp").forward(req,resp);
            } else{
            resp.getWriter().write(msg.getResult());
        }
    }
}
