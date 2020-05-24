package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Base;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.User;
import com.mjh.www.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseLoginDao implements BaseInterface{
    @Override
    public Msg login(Base base){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = JDBCUtil.getCon();
            String sql = "select * from merchant where merchant_email = ? and merchant_password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,base.getUserName());
            pstmt.setString(2,base.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()){
                MerChant merchant = new MerChant();
                merchant.setEmail(rs.getString("merchant_email"));
                merchant.setPassword(rs.getString("merchant_password"));
                merchant.setStatus(rs.getString("merchant_status"));
                merchant.setName(rs.getString("merchant_name"));
                merchant.setNameTwo(rs.getString("merchant_nameTwo"));
                merchant.setPhone(rs.getString("merchant_phone"));
                merchant.setSex(rs.getString("merchant_sex"));
                merchant.setSign(rs.getString("merchant_sign"));
                merchant.setHeadPitcher(rs.getString("merchant_head"));
                merchant.setAddr(rs.getString("merchant_addr"));
                return new Msg("商家登录成功",merchant);
            }
            sql = "select * from user where user_email = ? and user_password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,base.getUserName());
            pstmt.setString(2,base.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()){
                String email = rs.getString("user_email");
                String password = rs.getString("user_password");
                String name = rs.getString("user_name");
                String sex = rs.getString("user_sex");
                String phone = rs.getString("user_phone");
                String nameTwo = rs.getString("user_nameTwo");
                String status = rs.getString("user_status");
                String sign = rs.getString("user_sign");
                String headPitcher = rs.getString("user_headPitcher");
                String code = rs.getString("user_code");
                float money = rs.getFloat("user_money");
                User user = new User(email,password,name,sex,phone,headPitcher,nameTwo,sign,code,status);
                user.setMoney(money);
                return new Msg("用户登录成功",user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(con,rs,pstmt);
        }
        return new Msg("登录失败",null);
    }
}
