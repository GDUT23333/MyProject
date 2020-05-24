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
import org.codehaus.jackson.map.ObjectMapper;

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
import java.util.ArrayList;
import java.util.List;

/**
 * 修改商品信息
 */
@WebServlet("/modifyCommodityServlet")
public class ModifyCommodityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String title = "";
        String intro = "";
        Float price = 0.0f;
        String img = "";
        int id = 0;
        int category = 0;
        List<Pitcher> lists = new ArrayList<Pitcher>();
        //1、创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2、创建解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //3、保存路径
        String path = req.getServletContext().getRealPath("/view/img");
        //4、设置大小
        upload.setFileSizeMax(1024*1024);
        try{
            List<FileItem> files = upload.parseRequest(req);
            title = files.get(0).getString("utf-8");
            intro = files.get(1).getString("utf-8");
            price = Float.parseFloat(files.get(2).getString("utf-8"));
            id = Integer.parseInt(files.get(3).getString("utf-8"));
            category = Integer.parseInt(files.get(4).getString("utf-8"));
            if(files.size() > 5){
                for(int i = 5;i < files.size();i++){
                    FileItem file = files.get(i);
                    String href = CodeUtil.getCode()+file.getName() ;
                    File pitcher = FileUtil.createFile(href,path);
                    file.write(pitcher);//将文件部件写进文件中
                    Pitcher pit = new Pitcher(href);
                    lists.add(pit);
                }
                img = lists.get(0).getHref();
            }
        } catch (FileUploadBase.SizeLimitExceededException e) {
            resp.getWriter().write("图片不可以超过50kb");
            e.printStackTrace();
            return;
        } catch (FileUploadException e) {
            e.printStackTrace();
            resp.getWriter().write("修改失败");
            return;
        }catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("修改失败,请检查各项数据输入是否合法");
            return;
        }

        MarchantService marchantService = new MarchantService();
        MerChant one = (MerChant) req.getSession().getAttribute("one");
        Msg msg = marchantService.modifyCommodity(img,price,intro,title,id,lists,category,one);
        resp.getWriter().write(msg.getResult());
    }
}
