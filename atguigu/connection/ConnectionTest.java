package com.atguigu.connection;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Type 1.");
        TestConnection1();
        System.out.println("Type 2.");
        TestConnection2();
        System.out.println("Type 3.");
        TestConnection3();
        System.out.println("Type 4.");
        TestConnection4();
        System.out.println("Type 5.");
        TestConnection5();
    }

    public static void TestConnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","Duanxiaohong1966");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    public static void TestConnection2() throws Exception {
        //get the driver class by implementing the reflection method
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //provide the target database to connect
        String url = "jdbc:mysql://localhost:3306";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","Duanxiaohong1966");

        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    //DriverManager to replace driver class
    public static void TestConnection3() throws Exception {
        //1. get the driver class
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2. get the basic info of connections
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "Duanxiaohong1966";

        DriverManager.registerDriver(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //DriverManager to replace driver class
    public static void TestConnection4() throws Exception {
        //1. get the driver class
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. get the basic info of connections
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "Duanxiaohong1966";

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //5.final version, read in the setting files
    /*
     * Benefit is that we can change the property file
     * and less credential is being exposed
     * and separate the user data and code.
     */
    public static void TestConnection5() throws Exception {
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

    }


}
