package com.atguigu.DAOimproved;


import com.atguigu.bean.Stock;

import java.sql.Connection;
import java.util.List;

//this interface is used to define the standard operations for stock_list table
public interface StockDAO {
    //insert a stock to stock)list
    void insert(Connection conn, Stock stock);

    //delete a record based on id
    void deleteById(Connection conn, int id);

    //update the stock_list based on stock object
    void updateById(Connection conn, Stock stock);

    //query based on id
    Stock getById(Connection conn, int id);

    //get all the stock objects
    List<Stock> getAll(Connection conn);

    //return the number of rows
    Long getCount(Connection conn);

    //get by sector
    List<Stock> getBySector(Connection conn, String sector);
}
