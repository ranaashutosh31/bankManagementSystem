package dao;

import model.Account;
import model.Customer;
import util.DBConnection;
import java.sql.*;


public class CustomerDAO {
	//Creating a Customer
    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customer (name, email, phone_number, address) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getPhoneNumber());
            pst.setString(4, customer.getAddress());
            pst.executeUpdate();
        }
    }
    
    //Retrieving Customer by Customer Id
    public Customer getCustomerById(int customerId) throws SQLException {

        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        Customer customer = null;
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, customerId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) { 
                	customer = new Customer();
                	customer.setCustomerId(rs.getInt("customer_id"));
                	customer.setName(rs.getString("name"));
                	customer.setEmail(rs.getString("email"));
                	customer.setPhoneNumber(rs.getString("phone_number"));
                	customer.setAddress(rs.getString("address"));
                }
            }
        }
        return customer;
    }
    
  //Update Customer
    public boolean updateCustomerById(int customerId,String newName,String newEmail,String newPhoneNumber, String newAddress) throws SQLException {
        String query = "UPDATE Customer SET name = ?, email = ?,phone_number = ?, address = ?  WHERE customer_id = ?";
        boolean isUpdated = false;
        try (Connection con = DBConnection.getConnection()){
            try (PreparedStatement pst = con.prepareStatement(query)) {
                	pst.setString(1, newName);
                    pst.setString(2, newEmail);
                    pst.setString(3, newPhoneNumber);
                    pst.setString(4, newAddress);
                    pst.setInt(5, customerId);
                    int rowsAffected = pst.executeUpdate();
                    isUpdated = rowsAffected > 0;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }
    //Delete Customer
    public boolean deleteCustomerById(int customerId) {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        boolean isDeleted = false;
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement pst = con.prepareStatement(query)) {
            	pst.setInt(1, customerId);
                int rowsAffected = pst.executeUpdate();
                isDeleted = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return isDeleted;
    }
}
