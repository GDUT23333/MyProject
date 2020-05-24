package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Category;
import com.mjh.www.po.Page;
import com.mjh.www.po.Route;
import com.mjh.www.util.JDBCUtil;
import com.mjh.www.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PageShowDao implements PageShowInterface{
    /**
     * 首页的展示
     * @param page
     * @return
     */
    @Override
    public Msg searchAll(Page page) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Category> all = new ArrayList<Category>();
        List<Route> routes = new ArrayList<Route>();

        try{
            con = JDBCUtil.getCon();
            String sql = "select * from category";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Category category = new Category(rs.getInt("category_id"),rs.getString("category_name"));
                all.add(category);
            }
            sql = "select * from commodity where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            if(StringUtil.isEmpty(page.getInf())){
                sb.append("order by commodity_time desc  limit ?,?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setInt(1,page.getStartLine());
                pstmt.setInt(2,page.getPageSize());
                System.out.println(page.getStartLine());

            }
            else{
                sb.append(" and commodity_intro like ? or commodity_title like ? order by commodity_time desc limit ? , ?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setString(1,"%"+page.getInf()+"%");
                pstmt.setString(2,"%"+page.getInf()+"%");
                pstmt.setInt(3,page.getStartLine());
                pstmt.setInt(4,page.getPageSize());
            }
            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("commodity_id");
                String img = rs.getString("commodity_img");
                Float price = rs.getFloat("commodity_price");
                String introduce = rs.getString("commodity_intro");
                String title = rs.getString("commodity_title");
                Route route = new Route(img,price,introduce,title,id);
                routes.add(route);
            }
            page.setList(routes);
            page.setCategories(all);
            return new Msg("成功",page);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",null);
    }

    /**
     * 分页获得商家的上架商品
     * @param email
     * @param page
     * @return
     */
    @Override
    public Msg findAll(String email,Page page) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.getCon();
            String sql = "select * from commodity where commodity_mid = ? order by commodity_time desc limit ?,?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,email);
            pstmt.setInt(2,page.getStartLine());
            pstmt.setInt(3,page.getPageSize());
            rs = pstmt.executeQuery();
            List<Route> routes = new ArrayList<Route>();
            while(rs.next()){
                String img = rs.getString("commodity_img");
                String title = rs.getString("commodity_title");
                String intro = rs.getString("commodity_intro");
                int id = rs.getInt("commodity_id");
                int category = rs.getInt("commodity_c");
                float price = rs.getFloat("commodity_price");
                Route route = new Route(img,price,intro,title,id,category);
                routes.add(route);
            }
            page.setList(routes);
            return new Msg("成功",page);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("还未有上架任何商品",null);
    }

    /**
     * 商家上架商品的总记录,用于计算总页数
     * @param email
     * @return
     */
    @Override
    public Msg findMerchantTotalCount(String email) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select count(*) from commodity where commodity_mid = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,email);
            rs = pstmt.executeQuery();
            if(rs.next()){
                totalCount = rs.getInt("count(*)");
            }
            return new Msg("成功",totalCount);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",0) ;
    }

    /**
     * 搜寻商品的总记录数，用于计算页数
     * @param inf
     * @return
     */
    @Override
    public Msg findAllTotalCount(String inf) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet  rs = null;
        int totaoCount = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select count(*) from commodity where 1 = 1 ";
            StringBuilder sb = new StringBuilder(sql);
            if(StringUtil.isEmpty(inf)){
                pstmt = con.prepareStatement(sb.toString());
                rs = pstmt.executeQuery();
            }else{
                sb.append(" and commodity_intro like ? or commodity_title like ?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setString(1,"%"+inf+"%");
                pstmt.setString(2,"%"+inf+"%");
                rs = pstmt.executeQuery();
            }

            if(rs.next()){
                totaoCount = rs.getInt("count(*)");
                return new Msg("成功",totaoCount);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",0);
    }

}
