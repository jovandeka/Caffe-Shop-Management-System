package com.example.cafeshopmanagementsystem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class database {
    public static Connection conectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/caffe", "root", "");
            return connect;
        }catch (Exception e){e.printStackTrace();}
        return null;

    }
}
