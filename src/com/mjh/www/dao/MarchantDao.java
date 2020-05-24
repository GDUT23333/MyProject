package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.Page;
import com.mjh.www.po.Pitcher;
import com.mjh.www.po.Route;
import com.mjh.www.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MarchantDao implements MarchantDaoInterface {
    /**
     * 检验邮箱是否已经注册
     * @param email
     * @return
     */
    @Override
    public Msg checkEmail(String email) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.getCon();
            String sql = "select * from merchant where merchant_email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Msg("邮箱已被注册", null);
            }
            return new Msg("邮箱未被注册", email);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(con, rs, pstmt);
        }
        return new Msg("邮箱已被注册", null);
    }

    /**
     * 注册商家
     * @param merChant
     * @return
     */
    @Override
    public Msg registerMarc(MerChant merChant) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.getCon();
            String sql = "insert into merchant values(?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, merChant.getEmail());
            pstmt.setString(2, merChant.getPhone());
            pstmt.setString(3, merChant.getSex());
            pstmt.setString(4, merChant.getPassword());
            pstmt.setString(5, merChant.getName());
            pstmt.setString(6, merChant.getStatus());
            pstmt.setString(7, merChant.getCode());
            pstmt.setString(8, merChant.getSign());
            pstmt.setString(9, merChant.getNameTwo());
            pstmt.setString(10,merChant.getAddr());
            pstmt.setString(11,merChant.getHeadPitcher());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                return new Msg("注册成功", merChant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(con, rs, pstmt);
        }
        return new Msg("注册失败", null);
    }

    /**
     * 激活商家
     *
     * @param code
     * @return
     */
    @Override
    public Msg active(String code) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.getCon();
            String sql = "select * from merchant where merchant_code = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sql = "update merchant set merchant_status = ? where merchant_code = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "Y");
                pstmt.setString(2, code);
                int result = pstmt.executeUpdate();
                if (pstmt.executeUpdate() == 1) {
                    return new Msg("激活成功", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(con, rs, pstmt);
        }
        return new Msg("激活失败", null);
    }

    /**
     * 修改商家的信息
     * @param merChant
     * @return
     */
    @Override
    public Msg modify(MerChant merChant) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "update merchant set merchant_phone = ?,merchant_sex = ?,merchant_name = ?," +
                    "merchant_nameTwo = ?,merchant_sign = ?,merchant_head = ? where merchant_email = ?  and " +
                    "merchant_password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,merChant.getPhone());
            pstmt.setString(2,merChant.getSex());
            pstmt.setString(3,merChant.getName());
            pstmt.setString(4,merChant.getNameTwo());
            pstmt.setString(5,merChant.getSign());
            pstmt.setString(6,merChant.getHeadPitcher());
            pstmt.setString(7,merChant.getEmail());
            pstmt.setString(8,merChant.getPassword());

            int result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("修改成功",merChant);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("修改失败",null);
    }

    /**
     * 检验手机号是否已经被注册
     * @param phone
     * @return
     */
    @Override
    public Msg checkPhone(String phone) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JDBCUtil.getCon();
            String sql = "select * from merchant where merchant_phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Msg("手机已被注册", null);
            }
            return new Msg("手机未被注册", phone);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(con, rs, pstmt);
        }
        return new Msg("手机已被注册", null);
    }

    /**
     * 下架商品
     * @param id
     * @return
     */
    @Override
    public Msg deleteCommodity(int id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "delete from commodity where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            sql = "delete from routeimg where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            sql = "delete from routeseller where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            return new Msg("删除成功",null);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("删除失败",null);
    }

    /**
     * 修改上架的商品信息
     * @param route
     * @param merChant
     * @return
     */
    @Override
    public Msg modifyCommodity(Route route,MerChant merChant) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        String sql;
        try{
            con = JDBCUtil.getCon();
            List<Pitcher> lists = route.getPicture();
            if(lists.size() > 0){
                sql = "update commodity set commodity_img = ?,commodity_price = ?,commodity_intro = ?,commodity_title = ? where commodity_id = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,route.getImg());
                pstmt.setFloat(2,route.getPrice());
                pstmt.setString(3,route.getIntroduce());
                pstmt.setString(4,route.getTitle());
                pstmt.setInt(5,route.getId());
                result = pstmt.executeUpdate();
                if(result == 1){
                    sql = "delete from routeimg where commodity_id = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1,route.getId());
                    result = pstmt.executeUpdate();
                    if(result > 0){
                        sql = "insert into routeimg(commodity_id,small,route_id) values(?,?,?)";
                        for(Pitcher pitcher:lists){
                            pstmt = con.prepareStatement(sql);
                            pstmt.setInt(1,route.getId());
                            pstmt.setString(2,pitcher.getHref());
                            pstmt.setString(3,merChant.getEmail());
                            pstmt.executeUpdate();
                        }
                    }
                }
            }else{
                sql = "update commodity set commodity_price = ?,commodity_intro = ?,commodity_title = ? where commodity_id = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setFloat(1,route.getPrice());
                pstmt.setString(2,route.getIntroduce());
                pstmt.setString(3,route.getTitle());
                pstmt.setInt(4,route.getId());
                result = pstmt.executeUpdate();
            }
            return new Msg("修改成功",route);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("修改失败",null);
    }

    /**
     * 得到自己某个上架商品信息
     * @param id
     * @return
     */
    @Override
    public Msg checkDetail(int id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Route route = new Route();
        List<Pitcher> img = new ArrayList<Pitcher>();
        route.setId(id);
        try{
            con = JDBCUtil.getCon();
            String sql = "select * from commodity where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String title = rs.getString("commodity_title");
                String introduce = rs.getString("commodity_intro");
                String pitcher = rs.getString("commodity_img");
                float price = rs.getFloat("commodity_price");
                route.setIntroduce(introduce);
                route.setImg(pitcher);
                route.setTitle(title);
                route.setPrice(price);

            }
            sql = "select * from routeimg where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String one = rs.getString("small");
                int detailId = rs.getInt("id");
                Pitcher pitcher = new Pitcher(one,detailId);
                img.add(pitcher);
            }
            route.setPicture(img);
            sql = "select * from routeseller where commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String addr = rs.getString("seller_addr");
                String phone = rs.getString("seller_phone");
                String seller_name = rs.getString("seller_name");
                route.setSellerAddr(addr);
                route.setSellerName(seller_name);
                route.setSellerPhone(phone);
            }
            return new Msg("成功",route);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",null);
    }

    /**
     * 上架商品
     * @param route
     * @param merChant
     * @return
     */
    @Override
    public Msg addCommodity(Route route,MerChant merChant) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        int id = 0;
        List<Pitcher> lists = route.getPicture();
        try{
            con = JDBCUtil.getCon();
            String sql = "insert into commodity(commodity_c,commodity_img,commodity_price," +
                    "commodity_intro,commodity_title,commodity_mid) values(?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,route.getCategory());
            pstmt.setString(2,route.getImg());
            pstmt.setFloat(3,route.getPrice());
            pstmt.setString(4,route.getIntroduce());
            pstmt.setString(5,route.getTitle());
            pstmt.setString(6,merChant.getEmail());
            result = pstmt.executeUpdate();
            if(result == 1){
                sql = "select commodity_id from commodity where commodity_img = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,route.getImg());
                rs = pstmt.executeQuery();
                if(rs.next()){
                    id = rs.getInt("commodity_id");
                }
                sql = "insert into routeseller(commodity_id,seller_addr,seller_phone," +
                        "seller_name,seller_email) values(?,?,?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1,id);
                pstmt.setString(2,route.getSellerAddr());
                pstmt.setString(3,route.getSellerPhone());
                pstmt.setString(4,merChant.getNameTwo());
                pstmt.setString(5,merChant.getEmail());
                result = pstmt.executeUpdate();

                sql = "insert into routeimg(commodity_id,small,route_id) values(?,?,?)";
                for(Pitcher pitcher:lists){
                    pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1,id);
                    pstmt.setString(2,pitcher.getHref());
                    pstmt.setString(3,merChant.getEmail());
                    pstmt.executeUpdate();
                }
                return new Msg("增加成功",route);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("增加失败",null);
    }
}

