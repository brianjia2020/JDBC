package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Stock;
import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

public class StockForQuery {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //testQuert1();
        String sql = "SELECT * FROM stock_market.stock_list where id = ?";
        Stock stock = queryForStocks(sql, 1);
        if (stock == null){
            System.out.println("It's not found. ");
        } else {
            System.out.println(stock.getTicker());
            System.out.println(stock.toString());
        }
    }

    public static void testQuert1() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select id,ticker,company_name,exchange,currency,country,address,sector from stock_market.stock_list;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            //next is to check if the next record is empty or not
            //if there is return true and pointer moves downward, else return false
            int id = rs.getInt(1);
            String ticker = rs.getString(2);
            String company_name = rs.getString(3);
            String exchange = rs.getString(4);
            String currency = rs.getString(5);
            String country = rs.getString(6);
            String address = rs.getString(7);
            String sector = rs.getString(8);
            //best way to handle is to wrap it up in a class

            Stock stock = new Stock(id,ticker,company_name,exchange,currency,country,address,sector);
            System.out.println(stock.toString());

        }
        JDBCUtils.closeResource(conn,ps,rs);

    }

    public static Stock queryForStocks(String sql, Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            
            for(int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            System.out.println("ColumnCount is " + columnCount);
            if (rs.next()) {
                Stock stock = new Stock();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    //grab the column name
                    String columnname = rsmd.getColumnName(i + 1);
                    //send stock object the value of the result
                    //reflective
                    Field field = Stock.class.getDeclaredField(columnname);
                    field.setAccessible(true);
                    field.set(stock, value);
                }
                return stock;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            JDBCUtils.closeResource(conn, ps, rs);
        } catch(Exception e ){
            System.out.println(e.getMessage());
        }
        return null;

    }
}
