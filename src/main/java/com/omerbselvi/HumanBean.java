package com.omerbselvi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class HumanBean implements Serializable {

    public ArrayList<Human> getHumans() throws ClassNotFoundException, SQLException {

        Connection connect = null;

        String url = "jdbc:mysql://localhost:3306/AddressBookDB";

        String username = "";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }

        ArrayList<Human> humans = new ArrayList<Human>();
        PreparedStatement ps = connect.prepareStatement("SELECT human_id, name, surname, age, phone_number, address from Human");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Human human = new Human();
            human.setId(rs.getInt("human_id"));
            human.setName(rs.getString("name"));
            human.setSurname(rs.getString("surname"));
            human.setAge(rs.getInt("age"));
            human.setPhoneNumber(rs.getString("phone_number"));
            human.setAddress(rs.getString("address"));

            humans.add(human);

        }

        // close resources
        rs.close();
        ps.close();
        connect.close();

        return humans;

    }
}
