package com.atguigu.DAO;


import com.atguigu.bean.Customer;

import java.sql.Connection;

//this interface is used to define the standard operations for customers table
public interface CustomerDAO {
    void insert(Connection conn, Customer customer);
}
