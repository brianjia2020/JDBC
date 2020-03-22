package com.atguigu.ConnectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {

    public static void main(String[] args) {
        //testGetConnections();
        testGetConnections2();
    }

    //1. hard coded
    public static void testGetConnections(){
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306?&serverTimezone=UTC&rewriteBatchedStatements=true");
            cpds.setUser("root");
            cpds.setPassword("Duanxiaohong1966");

            //set initial connection pool size
            cpds.setInitialPoolSize(10);

            Connection conn = cpds.getConnection();
            System.out.println(conn.getTransactionIsolation());

            //destroy connection pool
            DataSources.destroy(cpds);

        } catch (SQLException | PropertyVetoException s){
            s.printStackTrace();
        }
    }

    //2. using configuration files
    public static void testGetConnections2(){
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        try {
            Connection conn = cpds.getConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
