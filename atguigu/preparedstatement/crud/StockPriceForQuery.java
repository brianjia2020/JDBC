package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Stock_price;
import com.atguigu.utility.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class StockPriceForQuery {
    public static void main(String[] args) {
        testQuery1();
    }

    public static void testQuery1(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql1 = "select * from stock_market.stock_price where id = ?";
            ps = conn.prepareStatement(sql1);
            ps.setObject(1, 1);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                int stock_id = rs.getInt(2);
                Date date = rs.getDate(3);
                float open_price = rs.getFloat(4);
                float close_price = rs.getFloat(5);
                float volume = rs.getFloat(6);
                float dividened = rs.getFloat(7);
                float split = rs.getFloat(8);

                Stock_price stock_price = new Stock_price(id, stock_id, date, open_price, close_price, volume, dividened, split);
                System.out.println(stock_price.toString());
            }
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }
        try {
            JDBCUtils.closeResource(conn, ps, rs);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
