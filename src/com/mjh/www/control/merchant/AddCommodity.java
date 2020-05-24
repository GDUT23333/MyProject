package com.mjh.www.control.merchant;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.Pitcher;
import com.mjh.www.po.Route;
import com.mjh.www.service.MarchantService;
import com.mjh.www.util.CodeUtil;
import com.mjh.www.util.FileUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 商家添加商品
 */
@WebServlet("/addCommodity")
public class AddCommodity extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //1、构建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2、构建解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //3、设置文件大小
        upload.setFileSizeMax(50*1024);
        //4、获取部件
        String path = req.getServletContext().getRealPath("/view/img");
        String title = "";
        String intro = "";
        int category = 0;
        float price = 0;
        String img = "";
        List<Pitcher> lists = new ArrayList<Pitcher>();
        try{
            List<FileItem> fileItems = upload.parseRequest(req);
            title = fileItems.get(0).getString("utf-8");
            intro = fileItems.get(1).getString("utf-8");
            category = Integer.parseInt(fileItems.get(3).getString("utf-8"));
            price = Float.parseFloat(fileItems.get(2).getString("utf-8"));
            for(int i = 4;i < fileItems.size();i++){
                FileItem fileItem = fileItems.get(i);
                String imgName = CodeUtil.getCode() + fileItem.getName();
                File file = FileUtil.createFile(imgName,path);
                fileItem.write(file);
                Pitcher pitcher = new Pitcher(imgName);
                lists.add(pitcher);
            }
            img = lists.get(0).getHref();
        } catch (FileUploadBase.SizeLimitExceededException e) {
            resp.getWriter().write("每张图片不可以超过50kb");
            e.printStackTrace();
            return;
        }catch (FileUploadException e) {
            resp.getWriter().write("新增失败");
            e.printStackTrace();
            return;
        } catch (Exception e) {
            resp.getWriter().write("新增失败，请检查各项数据输入是否合法");
            e.printStackTrace();
            return;
        }

        MerChant merChant = (MerChant)req.getSession().getAttribute("one");
        MarchantService marchantService = new MarchantService();
        Msg msg = marchantService.addCommodity(category,img,price,intro,title,merChant,lists);
        resp.getWriter().write(msg.getResult());

    }
}
