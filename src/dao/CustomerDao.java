package dao;

import core.DB;
import entities.Customer;
import entities.User;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {
    private Connection connection;

    public CustomerDao() { this.connection = DB.getInstance(); }

    public ArrayList<Customer> findAll() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM Customer");
            while (rs.next()) {
                customers.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }

    public boolean save(Customer customer) {
        String query = "INSERT INTO customer(name, type, phone, mail, address) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getMail());
            pr.setString(5, customer.getAddress());
            return pr.executeUpdate() != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public Customer getById(int id) {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                customer = this.match(rs);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customer;
    }

    public boolean update(Customer customer) {
        String query = "UPDATE customer SET name = ?, type = ?, phone = ?, mail = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query); // ? results a prepared statement
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getMail());
            pr.setString(5, customer.getAddress());
            pr.setInt(6, customer.getId());
            return pr.executeUpdate() != -1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM customer WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public ArrayList<Customer> query(String query) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while(rs.next()) {
                customers.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }

    public Customer match(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhone(rs.getString("phone"));
        customer.setMail(rs.getString("mail"));
        customer.setType(Customer.TYPE_ENUM.valueOf(rs.getString("type")));
        return customer;
    }
}
