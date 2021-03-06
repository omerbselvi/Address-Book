package com.omerbselvi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class HumanBean extends ConnectionBase implements Serializable {

    HumanBean(){
        super();
    }

    protected static final String DELETE = "DELETE FROM Human WHERE human_id = ?";

    public void deleteHumanAction(Human myItem){
        PreparedStatement ps = null;
        try {
            ps = connect.prepareStatement(DELETE);
            ps.setInt(1, myItem.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final static String IS_DUPLICATE = "SELECT COUNT(human_id) as cnt FROM Human WHERE name = ? AND surname = ?";
    public int isDuplicate(Human myItem){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = connect.prepareStatement(IS_DUPLICATE);
            ps.setString(1,myItem.getName());
            ps.setString(2,myItem.getSurname());
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected static final String INSERT = "INSERT INTO Human(name, surname, age, phone_number, address) VALUES (?, ?, ?, ?, ?)";

    public void insertHumanAction(Human myItem) throws SQLException{
        PreparedStatement ps = null;
        try {
            ps = connect.prepareStatement(INSERT);
            ps.setString(1, myItem.getName());
            ps.setString(2, myItem.getSurname());
            ps.setInt(3,myItem.getAge());
            ps.setString(4, myItem.getPhoneNumber());
            ps.setString(5, myItem.getAddress());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static final String SELECT_ALL = "SELECT human_id, name, surname, age, phone_number, address from Human";

    public ArrayList<Human> getHumans() throws ClassNotFoundException, SQLException {


        ArrayList<Human> humans = new ArrayList<Human>();
        PreparedStatement ps = connect.prepareStatement(SELECT_ALL);
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

        rs.close();
        ps.close();
        connect.close();

        return humans;

    }
}
