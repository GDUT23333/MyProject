package com.mjh.www.control.user;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.User;
import com.mjh.www.service.UserService;
import com.mjh.www.util.CodeUtil;
import com.mjh.www.util.FileUtil;
import com.mjh.www.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 用户个人信息的修改
 */
@WebServlet("/modifyUserServlet")
public class ModifyUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        /*File file = new File("E:/tempFile.txt");
        ServletInputStream inputStream = req.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int n = 0;
        while((n = inputStream.read(b)) != -1){
            fileOutputStream.write(b,0,n);
        }
        fileOutputStream.close();
        inputStream.close();*/
        String nameTwo = "";
        String headPitcher = "";
        String phone = "";
        String nameTrue = "";
        String sex = "";
        String sign = "";

        //1、创建部件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2、创建解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //3、解析req,返回部件链表
        String path = req.getServletContext().getRealPath("/view/headPitcher");
        //设置文件大小限制
        upload.setFileSizeMax(50*1024);
        User old = (User)req.getSession().getAttribute("two");
        try {
            List<FileItem> lists = upload.parseRequest(req);
            nameTwo = lists.get(0).getString("utf-8");
            FileItem file = lists.get(1);
            if(StringUtil.isEmpty(lists.get(1).getName())){
                headPitcher = old.getHeadPitcher();
            }else{
                headPitcher = CodeUtil.getCode()+lists.get(1).getName();
                File pitcher = FileUtil.createFile(headPitcher,path);
                file.write(pitcher);//将文件部件写进文件中
            }

            phone = lists.get(2).getString("utf-8");
            nameTrue = lists.get(3).getString("utf-8");
            sex = lists.get(4).getString("utf-8");
            sign = lists.get(5).getString("utf-8");
        }
        catch (FileUploadBase.FileSizeLimitExceededException  e) {
            resp.getWriter().write("文件超过50kb，请重新选择");
            e.printStackTrace();
            return;
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String email = old.getEmail();
        String password = old.getPassword();
        User reUser = new User(headPitcher,email,password,nameTrue,sex,phone,nameTwo,sign);
        UserService userService = new UserService();
        Msg msg = userService.modify(reUser);
        if("修改成功".equals(msg.getResult())){
            req.getSession().removeAttribute("two");
            req.getSession().setAttribute("two",reUser);
        }
        resp.getWriter().write(msg.getResult());
    }
}
