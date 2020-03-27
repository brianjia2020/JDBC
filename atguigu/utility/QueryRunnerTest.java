package com.atguigu.utility;

import com.atguigu.bean.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryRunnerTest {
    public static void main(String[] args) {
        /*
        List<Customer> beansets = queryByRunner();
        if (beansets != null) {
            beansets.forEach(System.out::println);
        }
         */

        testQuery8();
    }

    public static void insetByRunner(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "insert into test.customers(name, email, birth) values(?,?,?)";
            int insertCount = runner.update(conn,sql,"chunyang","bjia@nextpathway.com","1991-11-23");
            System.out.println("Have inserted" + insertCount + "records");


        } catch (Exception e ){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    public static List<Customer> queryByRunner(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "select name,email,birth from test.customers;";

            BeanListHandler<Customer> resultSetHandler = new BeanListHandler<Customer>(Customer.class);
            List<Customer> bean_sets = runner.query(conn,sql,resultSetHandler);
            bean_sets.forEach(System.out::println);
            return bean_sets;

        } catch (Exception e ){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }

        return null;
    }

    /*
     * self-defined result set handler
     */

    public static void testQuery8(){
        Connection conn = null;
        try{
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection();
            String sql = "select name,email,birth from test.customers;";
            ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet resultSet) throws SQLException {
                    return null;
                }
            };

            Customer customer = runner.query(conn,sql,handler);
            System.out.println(customer);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }


}
