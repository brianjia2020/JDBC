package com.atguigu.blob;


import com.atguigu.utility.JDBCUtils;

import java.io.*;
import java.sql.*;

//use prepared statement to update blob type data
public class BlobTest {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //testInsert();
        testQuery();
    }

    public static void testInsert() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into test.customers(name,email,birth,photo) values(?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1,"chunyang");
        ps.setObject(2,"brianjia1123@gmail.com");
        ps.setObject(3,"1991-11-23");
        FileInputStream is = new FileInputStream(new File("/Users/bjia/Desktop/Brian Personal Folder/0.jpeg"));
        ps.setBlob(4,is);
        ps.execute();

        JDBCUtils.closeResource(conn,ps);
    }

    public static void testQuery() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCUtils.getConnection();

        String sql = "select name, email, birth, photo from test.customers where name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"chunyang");
        InputStream is = null;
        FileOutputStream fos = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String name = rs.getString(1);
            String email = rs.getString(2);
            String birth = rs.getString(3);
            System.out.println(name+email+birth);

            //download the photo and save to local
            Blob photo = rs.getBlob(4);
            is = photo.getBinaryStream();
            fos = new FileOutputStream("chunyang.jpeg");
            byte [] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1){
                fos.write(buffer,0,len);
            }
        }

        JDBCUtils.closeResource(conn,ps,rs);
        assert fos != null;
        fos.close();
        is.close();

    }


}
