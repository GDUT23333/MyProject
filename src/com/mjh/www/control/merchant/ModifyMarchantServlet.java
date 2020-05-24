package com.mjh.www.control.merchant;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.MerChant;
import com.mjh.www.service.MarchantService;
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
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Random;

/**
 * 修改商家个人信息
 */
@WebServlet("/modifyMarchantServlet")
public class ModifyMarchantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
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
        MerChant old = (MerChant) req.getSession().getAttribute("one");
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

        /*ServletInputStream inputStream = req.getInputStream();
        String path = "E:/tempFile.txt";
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int n;
        while((n = inputStream.read(b))!=-1){
            fileOutputStream.write(b,0,n);
        }
        fileOutputStream.close();
        inputStream.close();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
*/
        String email = old.getEmail();
        String password = old.getPassword();
        MerChant newMerchant = new MerChant(sex,phone,nameTrue,nameTwo,sign,email,password,headPitcher);
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.modify(newMerchant);
        if("修改成功".equals(msg.getResult())){
            req.getSession().removeAttribute("one");
            req.getSession().setAttribute("one",newMerchant);
            resp.getWriter().write("修改成功");
        }else{
            resp.getWriter().write("修改失败");
        }
    }
}
