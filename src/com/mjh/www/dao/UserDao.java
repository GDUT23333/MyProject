package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.*;
import com.mjh.www.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao implements UserDaoInterface{
    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public Msg login(User user) {
        Connection con = JDBCUtil.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "select * from user where email = ?,password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getPassword());
            pstmt.setString(2,user.getPassword());
            rs = pstmt.executeQuery();
            return new Msg("登录成功",user);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("登录失败",null);
    }

    /**
     * 得到商品的总记录数，用来计算页数
     * @param id
     * @param inf
     * @return
     */
    @Override
    public Msg count(int id,String inf) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select count(*) from commodity where 1 = 1 ";
            StringBuilder sb = new StringBuilder(sql);
            if(id != 0){
                sb.append(" and commodity_c = ?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setInt(1,id);
            }
            else{
                sb.append(" and commodity_intro like ? or commodity_title like ?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setString(1,"%"+inf+"%");
                pstmt.setString(2,"%"+inf+"%");
            }
            rs = pstmt.executeQuery();
            if(rs.next()){
                totalCount = rs.getInt("count(*)");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("计算成功",totalCount);
    }

    /**
     * 模糊查询有商品
     * @param page
     * @return
     */
    @Override
    public Msg search(Page page) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = JDBCUtil.getCon();
            String sql = "select * from commodity where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            List<Route> list = new ArrayList<Route>();
            if(page.getId() != 0){
                sb.append(" and commodity_c = ? order by commodity_time desc limit ?,?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setInt(1,page.getId());
                pstmt.setInt(2,page.getStartLine());
                pstmt.setInt(3,page.getPageSize());
            }
            else{
                sb.append(" and commodity_intro like ? or commodity_title like ? limit ? , ?");
                pstmt = con.prepareStatement(sb.toString());
                pstmt.setString(1,"%"+page.getInf()+"%");
                pstmt.setString(2,"%"+page.getInf()+"%");
                pstmt.setInt(3,page.getStartLine());
                pstmt.setInt(4,page.getPageSize());
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                String img = rs.getString("commodity_img");
                String introduce = rs.getString("commodity_intro");
                float price = rs.getFloat("commodity_price");
                String title = rs.getString("commodity_title");
                int id = rs.getInt("commodity_id");
                Route route = new Route(img,price,introduce,title,id,page.getId());
                list.add(route);
            }
            return new Msg("成功",list);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",null);
    }

    /**
     * 得到商品的详细信息
     * @param id
     * @return
     */
    @Override
    public Msg find(int id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Route route = new Route();
        List<Pitcher> img = new ArrayList<Pitcher>();
        List<Comment> comments = new ArrayList<Comment>();
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
                Pitcher pitcher = new Pitcher(one);
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
            sql = "select  * from comment where comment_commodity = ? order by comment_data desc ";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String headPitcher = rs.getString("comment_headPitcher");
                String nameTwo = rs.getString("comment_user");
                String content = rs.getString("comment_content");
                Date date = rs.getDate("comment_data");
                String time = date.toString();
                Comment comment = new Comment(nameTwo,content,id,headPitcher);
                comment.setTime(time);
                comments.add(comment);
            }
            route.setComments(comments);

            return new Msg("成功",route);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",null);
    }

    /**
     * 判断用户是否已经注册
     * @param user
     * @return
     */
    @Override
    public Msg isRegister(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "select * from merchant where merchant_email = ? or merchant_phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setString(2,user.getPhone());
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Msg("手机或邮箱已经被注册",null);
            }
            sql = "select  * from user where user_email = ? or user_phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setString(2,user.getPhone());
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Msg("手机或邮箱已经被注册",null);
            }
            return new Msg("未被注册",null);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("手机或邮箱已经被注册",null);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public Msg register(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getName());
            pstmt.setString(4,user.getSex());
            pstmt.setString(5,user.getPhone());
            pstmt.setString(6,user.getNameTwo());
            pstmt.setString(7,user.getCode());
            pstmt.setString(8,user.getStatus());
            pstmt.setString(9,user.getSign());
            pstmt.setString(10,user.getHeadPitcher());
            pstmt.setFloat(11,user.getMoney());
            int result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("注册成功",user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("注册失败",null);
    }

    /**
     * 进行激活用户
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
            String sql = "select * from user where user_code = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sql = "update user set user_status = ? where user_code = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "Y");
                pstmt.setString(2, code);
                int result = pstmt.executeUpdate();
                if (result == 1) {
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
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public Msg modify(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "update user set user_name = ?,user_sex = ?,user_phone = ?,user_nameTwo = ?," +
                    "user_sign = ?,user_headPitcher = ? where user_email = ? and user_password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getSex());
            pstmt.setString(3,user.getPhone());
            pstmt.setString(4,user.getNameTwo());
            pstmt.setString(5,user.getSign());
            pstmt.setString(6,user.getHeadPitcher());
            pstmt.setString(7,user.getEmail());
            pstmt.setString(8,user.getPassword());
            result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("修改成功",user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("修改失败",null);
    }

    /**
     * 判断商品是否已经收藏，用于前端收藏按钮的显示变化
     * @param user
     * @param routeId
     * @return
     */
    @Override
    public Msg isFavourite(User user, int routeId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "select * from favourite where user_email = ? and commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setInt(2,routeId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Msg("已经收藏",null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("未收藏",null);
    }

    /**
     * 收藏商品
     * @param user
     * @param routeId
     * @return
     */
    @Override
    public Msg setFavourite(User user, int routeId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "insert into favourite(user_email,commodity_id) values(?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setInt(2,routeId);
            result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("收藏成功",null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("收藏失败",null);
    }

    /**
     * 取消收藏
     * @param user
     * @param routeId
     * @return
     */
    @Override
    public Msg cancelFavourite(User user, int routeId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "delete from favourite where user_email = ? and commodity_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setInt(2,routeId);
            result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("删除成功",null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("删除失败",null);
    }

    /**
     * 查看收藏
     * @param user
     * @param page
     * @return
     */
    @Override
    public Msg checkMyFavourite(User user, Page page) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Route> routes = new ArrayList<Route>();
        int routeId = 0;
        String title;
        String introduce;
        String img;
        float price = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select * from favourite where user_email = ? limit ?,?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            pstmt.setInt(2,page.getStartLine());
            pstmt.setInt(3,page.getPageSize());
            rs = pstmt.executeQuery();

            while (rs.next()){
                routeId = rs.getInt("commodity_id");
                pstmt = con.prepareStatement(sql);
                Route route = new Route();
                route.setId(routeId);
                routes.add(route);
            }
            if(routes.size() < 0){
                return new Msg("还未收藏任何东西",null);
            }
            sql = "select * from commodity  where commodity_id = ? order by commodity_time desc";
            pstmt = con.prepareStatement(sql);
            for(Route route:routes){
                pstmt.setInt(1,route.getId());
                rs = pstmt.executeQuery();
                if(rs.next()){
                    price = rs.getFloat("commodity_price");
                    introduce = rs.getString("commodity_intro");
                    title = rs.getString("commodity_title");
                    img = rs.getString("commodity_img");
                    route.setTitle(title);
                    route.setIntroduce(introduce);
                    route.setPrice(price);
                    route.setImg(img);
                }
            }
            page.setList(routes);
            return new Msg("查询成功",page);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("查找失败",null);
    }

    /**
     * 得到收藏的总数据数目，计算总页数
     * @param user
     * @return
     */
    @Override
    public Msg countMyFavourite(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select count(*) from favourite where user_email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getEmail());
            rs = pstmt.executeQuery();
            if(rs.next()){
                totalCount = rs.getInt("count(*)");
            }
            return new Msg("成功",totalCount);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",0);
    }

    /**
     * 评论商品
     * @param comment
     * @return
     */
    @Override
    public Msg giveComment(Comment comment) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "insert into comment(comment_content,comment_user,comment_commodity,comment_headPitcher) values(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,comment.getContent());
            pstmt.setString(2,comment.getUserNameTwo());
            pstmt.setInt(3,comment.getRouteId());
            pstmt.setString(4,comment.getUserHeadPitcher());
            result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("评论成功",comment);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("评论失败",null);
    }

    /**
     * 评论数据的总数目，调整前端显示
     * @param routeId
     * @return
     */
    @Override
    public Msg countComment(int routeId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "select count(*) from comment where comment_commodity = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,routeId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                totalCount = rs.getInt("count(*)");
            }
            return new Msg("成功",totalCount);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("失败",0);
    }

    /**
     * 支付后更新状态
     * @param user
     * @param
     * @return
     */
    @Override
    public Msg payFor(User user, float balance) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = JDBCUtil.getCon();
            String sql = "update user set user_money = ? where user_email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setFloat(1,balance);
            pstmt.setString(2,user.getEmail());
            result = pstmt.executeUpdate();
            if(result == 1){
                return new Msg("支付成功",user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("支付失败",null);
    }

}
