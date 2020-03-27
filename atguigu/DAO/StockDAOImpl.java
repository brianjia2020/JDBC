package com.atguigu.DAO;

import com.atguigu.bean.Stock;
import com.atguigu.utility.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
 * DAO: data access object
 */
public class StockDAOImpl extends baseDAO implements StockDAO{

    public static void main(String[] args) {
        StockDAOImpl stockDAO = new StockDAOImpl();
        Connection conn =null;
        try {
            conn = JDBCUtils.getConnection();
            Stock new_stock = new Stock(30,"CHUN","CHUNYANG INC.","canada","CAD","Canada","5 Gilmour Drive,Ajax,ON","Technology");
            //stockDAO.insert(conn,new_stock);
            System.out.println("Insert successfully.");
            //stockDAO.deleteById(conn,31);
            //System.out.println("deleted successfully.");
            new_stock.setCompany_name("Brian Jia Inc.");
            stockDAO.updateById(conn,new_stock);
            System.out.println("Name change to Brian Jia Inc.");

            Stock stock_12 = stockDAO.getById(conn,12);
            System.out.println("Got stock 12" + stock_12);

            //stockDAO.getAll(conn).forEach(System.out::println);

            Long count = stockDAO.getCount(conn);
            System.out.println("The count of the table stock_list is: " + count);
            System.out.println("All the technology companies are here.");
            stockDAO.getBySector(conn,"Technology").forEach(System.out::println);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
    @Override
    public void insert(Connection conn, Stock stock) {
        String sql = "insert into stock_market.stock_list(ticker,company_name, exchange, currency,country, address, sector) values (?,?,?,?,?,?,?)";
        update(conn,sql,stock.getTicker(),stock.getCompany_name(),stock.getExchange(),stock.getCurrency(),stock.getCountry(),stock.getAddress(),stock.getSector());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from stock_market.stock_list where id = ?";
        update(conn,sql,id);
    }

    @Override
    public void updateById(Connection conn, Stock stock) {
        String sql = "update stock_market.stock_list set ticker = ?,company_name = ?, exchange = ?, currency = ?,country = ?, address = ?, sector = ? where id = ?";
        update(conn,sql,stock.getTicker(),stock.getCompany_name(),stock.getExchange(),stock.getCurrency(),stock.getCountry(),stock.getAddress(),stock.getSector(),stock.getId());
    }

    @Override
    public Stock getById(Connection conn, int id) {
        String sql = "select id,ticker,company_name, exchange, currency,country, address, sector from stock_market.stock_list where id = ?";
        try {
            return getInstance(conn, Stock.class, sql, id);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Stock> getAll(Connection conn) {
        String sql = "select id,ticker,company_name, exchange, currency,country, address, sector from stock_market.stock_list;";
        try {
            return getforList(conn,Stock.class,sql);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from stock_market.stock_list;";
        return getValue(conn,sql);
    }

    @Override
    public List<Stock> getBySector(Connection conn, String sector) {
        String sql = "select id,ticker,company_name, exchange, currency,country, address, sector from stock_market.stock_list where sector = ?";
        try {
            return getforList(conn,Stock.class,sql,sector);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
