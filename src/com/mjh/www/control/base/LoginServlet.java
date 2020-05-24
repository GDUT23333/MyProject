package com.mjh.www.control.base;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Base;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.User;
import com.mjh.www.service.BaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录的servlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        BaseService baseService = new BaseService();
        Msg msg = baseService.login(name,password);
        String isRemember = req.getParameter("isRember");
        System.out.println(isRemember);
        System.out.println(name);
        if("商家登录成功".equals(msg.getResult())){
            MerChant reMer = (MerChant)msg.getMessage();
            if("Y".equals(reMer.getStatus())){
                HttpSession session = req.getSession();
                session.setAttribute("one",reMer);
                if("Y".equals(isRemember)){
                    Cookie[] cookies = req.getCookies();
                    Boolean flag = true;//判断cookie里面保存的内容是不是与用户名密码一一对应
                    //第一次设置
                    if(cookies != null&&cookies.length == 0){
                        Cookie userName = new Cookie("userName",name);
                        Cookie userPassword = new Cookie("userPassword",password);
                        userName.setMaxAge(60*60*24*7);
                        userPassword.setMaxAge(60*60*24*7);
                        resp.addCookie(userName);
                        resp.addCookie(userPassword);
                    }
                    //不是第一次设置，遍历是否还是同一个，不是同一个就新建
                    else{
                        for(Cookie cookie:cookies){
                            if(cookie.getValue().equals(name)){
                                flag = false;
                                break;
                            }
                        }
                        //销毁所有cookie，再新建
                        if(flag){
                            for(Cookie cookie:cookies){
                                cookie.setMaxAge(0);
                            }
                            Cookie userName = new Cookie("userName",name);
                            Cookie userPassword = new Cookie("userPassword",password);
                            userName.setMaxAge(60*60*24*7);
                            userPassword.setMaxAge(60*60*24*7);
                            resp.addCookie(userName);
                            resp.addCookie(userPassword);
                        }
                    }
                return;
            }else{
                resp.getWriter().write("未进行激活");
                return;
                }
            }
        if("用户登录成功".equals(msg.getResult())){
            User reUser = (User)msg.getMessage();
            if("Y".equals(reUser.getStatus())){
                HttpSession session = req.getSession();
                session.setAttribute("two",reUser);
                if("Y".equals(isRemember)){
                    Cookie[] cookies = req.getCookies();
                    Boolean flag = true;//判断cookie里面保存的内容是不是与用户名密码一一对应
                    //第一次设置
                    if(cookies != null&&cookies.length == 0){
                        Cookie userName = new Cookie("userName",name);
                        Cookie userPassword = new Cookie("userPassword",password);
                        userName.setMaxAge(60*60*24*7);
                        userPassword.setMaxAge(60*60*24*7);
                        resp.addCookie(userName);
                        resp.addCookie(userPassword);
                    }
                    //不是第一次设置
                    else{
                        for(Cookie cookie:cookies){
                            if(cookie.getValue().equals(name)){
                                flag = false;
                                break;
                            }
                        }
                        //销毁所有cookie，再新建
                        if(flag){
                            for(Cookie cookie:cookies){
                                cookie.setMaxAge(0);
                            }
                            Cookie userName = new Cookie("userName",name);
                            Cookie userPassword = new Cookie("userPassword",password);
                            userName.setMaxAge(60*60*24*7);
                            userPassword.setMaxAge(60*60*24*7);
                            resp.addCookie(userName);
                            resp.addCookie(userPassword);
                        }
                    }
                }
                resp.getWriter().write(msg.getResult());
                return;
            }else{
                resp.getWriter().write("未进行激活");
                return;
            }
        }
        resp.getWriter().write("登录失败，请检查用户名和密码");
        }
    }
}
