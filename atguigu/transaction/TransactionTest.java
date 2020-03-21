package com.atguigu.transaction;

import com.atguigu.bean.User;
import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/*
 * 1. what is database transaction
 *    a transaction is a group of actions changing the data from one state to another
 *    a group of DML
 * 2. transaction principle
 *    either all the transactions are processed or committed or all the transaction are being rolled back
 *    Middle state is not tolerated.
 * 3. once data committed, cannot rollback
 * 4. what operations can cause auto commit
 *    DDL
 *    DML, default to be auto commit
 *         by doing to turn auto commit off, set autocommit = false
 * 5. strategy is
 *      5.1 get connection
 *      5.2 execute the commands
 *      5.3 close connection
 */
public class TransactionTest {
    public static void main(String[] args) {
        try {
            //testUpdate();
            testTransactionSelect();
            testTransactionUpdate();
            testTransactionSelect();
        } catch (IOException | SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * when AA transfers money to BB, there are two update statement that happens
     * 1. update user_table set balance = balance - 100 where user = 'AA';
     * 2. update user_table set balance = balance + 100 where user = 'BB';
     */
    public static void testUpdate(){
        /*
        String sql1 = "update test.user_table set balance = balance + 100 where user = ?";
        update(sql1,"AA");

        //mimic network problem
        //System.out.println(10/0);

        String sql2 = "update test.user_table set balance = balance - 100 where user = ?";
        update(sql2,"BB");

        System.out.println("transfer successful.");
        */
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            System.out.println(conn.getAutoCommit());
            conn.setAutoCommit(false);
            String sql1 = "update test.user_table set balance = balance - 100 where user = ?";
            update(conn,sql1,"AA");

            //mimic network problem
            //System.out.println(10/0);

            String sql2 = "update test.user_table set balance = balance + 100 where user = ?";
            update(conn,sql2,"BB");

            System.out.println("transfer successful.");
            conn.commit();

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();

            //rollback
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException c){
                c.printStackTrace();
            }
        } finally {
            try{
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
                JDBCUtils.closeResource(conn,null);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }


    /*
     * generic
     */
    public static int update(String sql, Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i =0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }

            return ps.executeUpdate();

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUtils.closeResource(conn, ps);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return 0;
    }

    /*
     * considering database transaction
     */
    public static int update(Connection conn, String sql, Object...args){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            for(int i =0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUtils.closeResource(null, ps);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return 0;
    }

    //***********************************************************************

    public static void testTransactionSelect() throws SQLException, IOException, ClassNotFoundException {

        Connection conn = JDBCUtils.getConnection();

        System.out.println(conn.getTransactionIsolation());
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        conn.setAutoCommit(false);

        String sql = "select user,password,balance from test.user_table where user = ?";
        User user = getInstance(conn, User.class,sql,"AA");
        System.out.println(user);
    }

    public static void testTransactionUpdate() throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        Connection conn = JDBCUtils.getConnection();
        conn.setAutoCommit(false);
        String sql = "update test.user_table set balance = ? where user = ?";
        update(conn,sql,2000,"AA");
        Thread.sleep(15000);
        System.out.println("Update completed.");
    }

    //generic query operation, return an object reflecting one database record
    public static <T> T getInstance(Connection conn,Class<T> clazz,String sql, Object...args) throws SQLException {
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i = 0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                T t = clazz.newInstance();
                for (int i = 0;i < columnCount; i++){
                    Object columnValue = rs.getObject(i+1);
                    String columnLabel = rsmd.getColumnLabel(i+1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (IOException | SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }


}
