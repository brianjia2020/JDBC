package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Stock;
import com.atguigu.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import com.atguigu.utility.JDBCUtils;

/*
 * using prepared statement to replace statement and do the crud operation for the database
 * 1. create,delete,update - no result coming
 * 2. select - result coming back
 */
public class PreparedStatementUpdateTest {
    public static void main(String[] args) throws Exception {
        //1. add a
        //testInsert();
        //testModify();
        String sql = "delete from test.user where user = ?";
        runCommand(sql,"AA");

    }

    public static void testInsert() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1. connection
            conn = JDBCUtils.getConnection();

            //2. prepared
            String sql = "insert into test.user (user, password,balance) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            //3. add data
            ps.setString(1,"Duan xiaohong");
            ps.setString(2,"Duanxiaohong1966");
            ps.setInt(3,999999);
            //4.execute the statement
            ps.execute();
            //5.close the connection
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtils.closeResource(conn, ps,null);
        }
    }

    public static void testModify() throws SQLException, IOException, ClassNotFoundException {
        //1. get connection
        Connection conn = JDBCUtils.getConnection();
        //2. precompiled prepared statement
        String sql = "update test.user set password = ? where user = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        //3. fill in the question mark
        ps.setObject(1, "chunyang");
        ps.setObject(2,"AA");
        //4. execute
        ps.execute();
        //5. close the resource
        JDBCUtils.closeResource(conn,ps,null);
    }

    public static void runCommand(String sql, Object ...args) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        //fill the question mark
        for(int i = 0;i<args.length;i++){
            ps.setObject(i+1,args[i]);
        }
        ps.execute();
        JDBCUtils.closeResource(conn,ps,null);
    }


}
