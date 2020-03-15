package com.atguigu.statement.crud;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class StatementTest {
    public static void main(String[] args) {
        testLogin();
    }

    public static void testLogin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please inout user name: ");
        String user = scanner.next();
        System.out.println("Please input password: ");
        String password = scanner.next();

        String sql = "select user,password from test.user where user= '" + user + "' and password= '" + password + "';";

    }

}
