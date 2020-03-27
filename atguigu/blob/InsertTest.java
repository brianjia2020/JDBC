package com.atguigu.blob;

import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//use prepared statement to do the operation of massive insertion
//first way is to use raw statement to insert in a loop, but very inefficient
//insert 20,000 records into goods statement
public class InsertTest {
    public static void main(String[] args) {
        insertTest1();
        insertTest2();
        insertTest3();
    }

    public static void insertTest1(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();

            String sql = "insert into test.goods(name) values(?)";

            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 200000; i++) {
                ps.setObject(1, "name_" + i);
                ps.execute();
            }

            long end = System.currentTimeMillis();
            System.out.println("Time spent in executing the 20000 insertion is " + (end-start));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(conn, ps);

    }
    //1. addBatch() 2. executeBatch() 3.clearBatch()
    public static void insertTest2(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into test.goods(name) values(?)";

            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 200000; i++) {
                ps.setObject(1, "name_" + i);
                ps.addBatch();
                if (i % 500 == 0) {
                    //execute the batch
                    ps.executeBatch();
                    //clear the batch
                    ps.clearBatch();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("Time spent in executing the 20000 insertion is " + (end-start));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(conn, ps);

    }

    public static void insertTest3(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into test.goods(name) values(?)";

            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 200000; i++) {
                ps.setObject(1, "name_" + i);
                ps.addBatch();
                if (i %500 == 0){
                    //execute the batch
                    ps.executeBatch();
                    //clear the batch
                    ps.clearBatch();
                }
            }
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println("Time spent in executing the 20000 insertion is " + (end-start));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(conn, ps);

    }



}
