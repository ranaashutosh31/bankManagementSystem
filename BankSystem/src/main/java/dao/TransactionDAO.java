package dao;

import model.Transaction;
import util.DBConnection;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
	//Deposit
    public void addTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO Transaction (account_number, transaction_type, amount) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, transaction.getAccountNumber());
            pst.setString(2, transaction.getTransactionType());
            pst.setDouble(3, transaction.getAmount());
            pst.executeUpdate();
        }
    }
    //Withdraw
    public boolean withdrawFunds(int accountNumber, double amount) throws SQLException {
        String checkBalanceQuery = "SELECT balance FROM Account WHERE account_number = ?";
        String updateBalanceQuery = "UPDATE Account SET balance = balance - ? WHERE account_number = ?";
        String insertTransactionQuery = "INSERT INTO Transaction (account_number, transaction_type, amount, transaction_date) VALUES (?, 'withdrawal', ?, NOW())";

        try (Connection con = DBConnection.getConnection()) {
            //Check if there are sufficient funds
            double currentBalance = 0;
            try (PreparedStatement checkBalanceStmt = con.prepareStatement(checkBalanceQuery)) {
                checkBalanceStmt.setInt(1, accountNumber);
                try (ResultSet rs = checkBalanceStmt.executeQuery()) {
                    if (rs.next()) {
                        currentBalance = rs.getDouble("balance");
                    } else {
                        // Account not found
                        return false;
                    }
                }
            }

            if (currentBalance < amount) {
                // Insufficient funds
                return false;
            }

            //Deduct the amount from the account balance
            try (PreparedStatement updateBalanceStmt = con.prepareStatement(updateBalanceQuery)) {
                updateBalanceStmt.setDouble(1, amount);
                updateBalanceStmt.setInt(2, accountNumber);
                updateBalanceStmt.executeUpdate();
            }

            //Record the transaction
            try (PreparedStatement insertTransactionStmt = con.prepareStatement(insertTransactionQuery)) {
                insertTransactionStmt.setInt(1, accountNumber);
                insertTransactionStmt.setDouble(2, amount);
                insertTransactionStmt.executeUpdate();
            }

            return true;
        }
    }
    
    //Transfer Funds
    public boolean transferFunds(int sourceAccountNumber, int destinationAccountNumber, double amount) throws SQLException {
        String checkBalanceQuery = "SELECT balance FROM Account WHERE account_number = ?";
        String deductAmountQuery = "UPDATE Account SET balance = balance - ? WHERE account_number = ?";
        String addAmountQuery = "UPDATE Account SET balance = balance + ? WHERE account_number = ?";
        String insertTransactionQuery = "INSERT INTO Transaction (account_number, transaction_type, amount, transaction_date) VALUES (?, ?, ?, NOW())";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);  // Start transaction

            // Step 1: Check if source account has sufficient balance
            double currentBalance = 0;
            try (PreparedStatement checkBalanceStmt = con.prepareStatement(checkBalanceQuery)) {
                checkBalanceStmt.setInt(1, sourceAccountNumber);
                try (ResultSet rs = checkBalanceStmt.executeQuery()) {
                    if (rs.next()) {
                        currentBalance = rs.getDouble("balance");
                    } else {
                        con.rollback();
                        return false; // Source account not found
                    }
                }
            }

            if (currentBalance < amount) {
                con.rollback();
                return false; // Insufficient funds
            }

            // Step 2: Deduct amount from source account
            try (PreparedStatement deductAmountStmt = con.prepareStatement(deductAmountQuery)) {
                deductAmountStmt.setDouble(1, amount);
                deductAmountStmt.setInt(2, sourceAccountNumber);
                deductAmountStmt.executeUpdate();
            }

            // Step 3: Add amount to destination account
            try (PreparedStatement addAmountStmt = con.prepareStatement(addAmountQuery)) {
                addAmountStmt.setDouble(1, amount);
                addAmountStmt.setInt(2, destinationAccountNumber);
                int rowsUpdated = addAmountStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    con.rollback();
                    return false; // Destination account not found
                }
            }

            // Step 4: Record both debit and credit transactions
            try (PreparedStatement insertTransactionStmt = con.prepareStatement(insertTransactionQuery)) {
                // Record debit for source account
                insertTransactionStmt.setInt(1, sourceAccountNumber);
                insertTransactionStmt.setString(2, "transfer withdrawal");
                insertTransactionStmt.setDouble(3, amount);
                insertTransactionStmt.executeUpdate();

                // Record credit for destination account
                insertTransactionStmt.setInt(1, destinationAccountNumber);
                insertTransactionStmt.setString(2, "transfer deposit");
                insertTransactionStmt.setDouble(3, amount);
                insertTransactionStmt.executeUpdate();
            }

            con.commit();  // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //View Transaction History
    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException {
        String query = "SELECT * FROM Transaction WHERE account_number = ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, accountNumber);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setAccountNumber(rs.getInt("account_number"));
                    transaction.setTransactionType(rs.getString("transaction_type"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return transactions;
    }
}
