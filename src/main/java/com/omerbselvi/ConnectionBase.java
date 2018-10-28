package com.omerbselvi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBase {
    Connection connect;
    ConnectionBase(){

        connect = null;

        String url = "jdbc:mysql://localhost:3306/AddressBookDB?autoReconnect=true&useSSL=false";

        String username = "jsf";
        String password = "password";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
