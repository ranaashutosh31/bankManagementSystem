package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Account;
import model.Customer;
import model.Transaction;
import java.sql.SQLException;
import java.util.List;

public class BankingService {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    //CREATE NEW ACCOUNT
    public void createAccount(Account account) throws SQLException {
        accountDAO.addAccount(account);
    }
    
    //VIEW ACCOUNT DETAILS
    public void viewAccountDetailsByAccountNumber(int accountNumber) throws SQLException {
    	Account account = accountDAO.getAccountByNumber(accountNumber);
    	if (account != null) {
            System.out.println("Account found: " + account);
        } else {
            System.out.println("Account not found.");
        }
    }
    
    //UPDATE ACCOUNT BY ACCOUNT NUMBER
    public void updateAccountDetailsByAccountNumber(int accountNumber,int newCustomerID,double newBalance,String newType) throws SQLException {
    	boolean isUpdated = accountDAO.updateAccountByNumber(accountNumber,newCustomerID,newBalance,newType);
    	if (isUpdated == true) {
            System.out.println("Account Updated Succesfully.");
        } else {
            System.out.println("Account update was not done.");
        }
    }
    
    //DELETE ACCOUNT
    public void deleteAccountByNumber(int accountNumber) {
    	boolean isDeleted = accountDAO.deleteAccountByNumber(accountNumber);
    	if (isDeleted == true) {
            System.out.println("Account Deleted Successfully.");
        } else {
            System.out.println("Account deletion was not done.");
        }
    }
    
    //CREATE NEW CUSTOMER
    public void createCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }
    
    //VIEW CUSTOMER DETAILS BY CUSTOMER ID
    public void viewCustomerDetailsByCustomerId(int customerId) throws SQLException {
    	Customer customerPerson = customerDAO.getCustomerById(customerId);
    	if (customerPerson != null) {
            System.out.println("Customer found: " + customerPerson);
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    //UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    public void updateCustomerDetailsByCustomerId(int customerId,String newName,String newEmail,String newPhoneNumber, String newAddress) throws SQLException {
    	boolean isUpdated = customerDAO.updateCustomerById(customerId,newName,newEmail,newPhoneNumber,newAddress);
    	if (isUpdated == true) {
            System.out.println("Customer Details Updated Succesfully.");
        } else {
            System.out.println("Customer Detailss update was not done.");
        }
    }
    
    //DELETE CUSTOMER
    public void deleteCustomerById(int customerId) {
    	boolean isDeleted = customerDAO.deleteCustomerById(customerId);
    	if (isDeleted == true) {
            System.out.println("Customer Deleted Successfully.");
        } else {
            System.out.println("Customer deletion was not done.");
        }
    }

    //Deposit
    public void deposit(int accountNumber, double amount) throws SQLException {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountDAO.updateAccount(account);
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionType("deposit");
        transaction.setAmount(amount);
		transactionDAO.addTransaction(transaction);
    }
    
    //Withdraw
    public boolean withdrawFunds(int accountNumber, double amount) {
        try {
            return transactionDAO.withdrawFunds(accountNumber, amount);
        } catch (SQLException e) {
            System.out.println("Error while withdrawing funds: " + e.getMessage());
            return false;
        }
    }
    
    //Transfer Funds
    public boolean transferFunds(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        try {
            return transactionDAO.transferFunds(sourceAccountNumber, destinationAccountNumber, amount);
        } catch (SQLException e) {
            System.out.println("Error during fund transfer: " + e.getMessage());
            return false;
        }
    }
    
    //View Transaction History
    public List<Transaction> getTransactionHistory(int accountNumber) {
        try {
            return transactionDAO.getTransactionHistory(accountNumber);
        } catch (SQLException e) {
            System.out.println("Error fetching transaction history: " + e.getMessage());
            return null;
        }
    }

}
