package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Stock;
import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * a generic query for all different tables
 */
public class PreparedStatementQueryTest {
    public static void main(String[] args){
        String sql = "select id, ticker,exchange, currency,country,address,sector from stock_market.stock_list where id =?";
        try {
            System.out.println(getInstance(Stock.class,sql,1));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        sql = "select id, ticker,exchange, currency,country,address,sector from stock_market.stock_list;";
        try{
            List<Stock> result = getforList(Stock.class,sql);
            result.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static <T> List<T> getforList(Class<T> clazz, String sql, Object...args) throws SQLException {
        Connection conn = null;
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
            //create
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0;i < columnCount; i++){
                    Object columnValue = rs.getObject(i+1);
                    String columnLabel = rsmd.getColumnLabel(i+1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (IOException | SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }

        return null;
    }


    public static <T> T getInstance(Class<T> clazz,String sql, Object...args) throws SQLException {
        Connection conn = null;
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
            JDBCUtils.closeResource(conn,ps,rs);
        }

        return null;
    }

}
