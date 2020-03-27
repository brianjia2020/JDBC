package com.atguigu.utility;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu.connection.ConnectionTest;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//a group of utility for
public class JDBCUtils {
    public static void main(String[] args) throws Exception {
        System.out.println(getConnectionByDruid());
    }
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("com/atguigu/jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
        return conn;
    }

    public static void closeResource(Connection conn, Statement ps, ResultSet rs) throws SQLException {
        if (ps != null){
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    public static void closeResource(Connection conn, PreparedStatement ps) {

        try {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    /*
     * using dbutils.jar
     */
    public static void closeResourcebyCommons(Connection conn, Statement ps, ResultSet rs){
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }

    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

    public static Connection getConnectionbyC3P0 () throws SQLException{
        Connection conn = cpds.getConnection();
        System.out.println(conn);
        return conn;
    }


    private static DataSource source1;
    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnectionByDruid() throws Exception{
        Connection connection = source1.getConnection();
        return connection;
    }

    }
