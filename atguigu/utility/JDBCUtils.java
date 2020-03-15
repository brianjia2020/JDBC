package com.atguigu.utility;

import com.atguigu.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//a group of utility for
public class JDBCUtils {
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
}
