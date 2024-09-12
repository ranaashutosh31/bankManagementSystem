package dao;

import model.Account;
import util.DBConnection;
import java.sql.*;


public class AccountDAO {
	//Create New Account
    public void addAccount(Account account) throws SQLException {
        String query = "INSERT INTO Account (customer_id, balance, type) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, account.getCustomerId());
            pst.setDouble(2, account.getBalance());
            pst.setString(3, account.getType());
            pst.executeUpdate();
        }
    }
    //Retrieving an Account by Account Number
    public Account getAccountByNumber(int accountNumber) throws SQLException {
        String query = "SELECT * FROM Account WHERE account_number = ?";
        Account account = null;
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, accountNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) { 
                    account = new Account();
                    account.setAccountNumber(rs.getInt("account_number"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setBalance(rs.getDouble("balance"));
                    account.setType(rs.getString("type"));
                }
            }
        }
        return account;
    }
    //Update Account 
    public boolean updateAccountByNumber(int accountNumber,int newCustomerID,double newBalance,String newType) throws SQLException {
        String query = "UPDATE Account SET customer_id = ?, balance = ?,type = ?  WHERE account_number = ?";
        boolean isUpdated = false;
        try (Connection con = DBConnection.getConnection()){
            try (PreparedStatement pst = con.prepareStatement(query)) {
                	pst.setInt(1, newCustomerID);
                    pst.setDouble(2, newBalance);
                    pst.setString(3, newType);
                    pst.setInt(4, accountNumber);
                    int rowsAffected = pst.executeUpdate();
                    isUpdated = rowsAffected > 0;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }
    //Delete Account
    public boolean deleteAccountByNumber(int accountNumber) {
        String query = "DELETE FROM Account WHERE account_number = ?";
        boolean isDeleted = false;
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement pst = con.prepareStatement(query)) {
            	pst.setInt(1, accountNumber);
                int rowsAffected = pst.executeUpdate();
                isDeleted = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return isDeleted;
    }
    
    //Update Account for deposit transaction
    public void updateAccount(Account account) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("UPDATE Account SET ");
        boolean isFirst = true;

        if (account.getBalance() > -1) {
            queryBuilder.append("balance = ?");
            isFirst = false;
        }
        if (account.getType() != null) {
            if (!isFirst) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("type = ?");
            isFirst = false;
        }
        if (account.getCustomerId() != 0) {
            if (!isFirst) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("customer_id = ?");
        }
        queryBuilder.append(" WHERE account_number = ?");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(queryBuilder.toString())) {

            int parameterIndex = 1;

            if (account.getBalance() > -1) {
                pst.setDouble(parameterIndex++, account.getBalance());
            }
            if (account.getType() != null) {
                pst.setString(parameterIndex++, account.getType());
            }
            if (account.getCustomerId() != 0) {
                pst.setInt(parameterIndex++, account.getCustomerId());
            }
            pst.setInt(parameterIndex, account.getAccountNumber());

            pst.executeUpdate();
        }
    }
}
