package com.mjh.www.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 连接数据库与关闭资源
 */
public class JDBCUtil {
    private static String name = "root";
    private static String password = "admin";
    private static String url = "jdbc:mysql://localhost:3306/db_shopping?useUnicode=true&characterEncoding=UTF8&useSSL=false";
    private static ArrayList<Connection> conlist = new ArrayList<>();
    //创建连接池
    static{
        for(int i = 0;i < 5;i++){
            Connection con = createCon();
            conlist.add(con);
        }
    }
    //从连接池取连接
    public static Connection getCon(){
        if(conlist.isEmpty()==false){
            Connection con = conlist.get(0);
            conlist.remove(con);
            return con;
        }else{
            return createCon();
        }
    }

    public static Connection createCon(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,name,password);
            return con;
        }catch(Exception e){
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return null;
    }
    //关闭资源
    public static void close(Connection con, ResultSet rs, Statement stmt) {
        try {
            if (con != null) {
                conlist.add(con);//拿回连接池
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
