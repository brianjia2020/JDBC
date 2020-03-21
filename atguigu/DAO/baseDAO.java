package com.atguigu.DAO;

import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * encapsulate the common CRUD operation
 */
public abstract class baseDAO {

    public static <T> List<T> getforList(Connection conn, Class<T> clazz, String sql, Object...args) throws SQLException {
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
            JDBCUtils.closeResource(null,ps,rs);
        }

        return null;
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

    //get some generic special value, like count(*),date
    public static <E>E getValue(Connection conn, String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for(int i =0; i < args.length; i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                JDBCUtils.closeResource(null,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

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


}
