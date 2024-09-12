package CognizantCaseStudy.BankSystem;

import model.Account;
import model.Customer;
import model.Transaction;
import service.BankingService;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;


public class App {

    private static final BankingService service = new BankingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            switch (choice) {
	            case 1 : accountMenu(); return;
	            case 2 : transactionMenu(); return;
	            case 3 : customerMenu(); return;
	            case 0 : {
	                System.out.println("Exiting application.");
	                return;
	            }
	            default : System.out.println("Invalid choice. Try again.");
	        }
        }
    }
    
    //MAIN MENU
    private static void showMainMenu() {
        System.out.println("1. Account Management");
        System.out.println("2. Transaction Management");
        System.out.println("3. Customer Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    //ACCOUNT MENU
    private static void accountMenu() {
    	while(true) {
    		System.out.println("1. Create New Account");
            System.out.println("2. View account details by Account Number");
            System.out.println("3. Update Account Information");
            System.out.println("4. Close an Account");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
    	        case 1 : createNewAccount(); return;
    	        case 2 : viewAccountByAccNumber(); return;
    	        case 3 : updateAccountInfo(); return;
    	        case 4 : closeAccount(); return;
    	        case 0 : {
    	            return;
    	        }
    	        default : System.out.println("Invalid choice. Try again.");
    	    }
    	}
        
    }

    //TRANSACTION MENU
    private static void transactionMenu() {
    	while(true) {
    		System.out.println("1. Deposit Funds");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Transfer Funds");
            System.out.println("4. View Transaction History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
    	        case 1 : depositFunds(); return;
    	        case 2 : withdrawFunds(); return;
    	        case 3 : transferFunds(); return;
    	        case 4 : viewTransactionHistory(); return;
    	        case 0 : {
    	            return;
    	        }
    	        default : System.out.println("Invalid choice. Try again.");
    	    }
    	}
        
    }

	//CUSTOMER MENU
    private static void customerMenu() {
    	while(true) {
    		System.out.println("1. Register a new Customer");
            System.out.println("2. View customer details by Customer Id");
            System.out.println("3. Update Customer Information");
            System.out.println("4. Delete a Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
    	        case 1 : createNewCustomer(); return;
    	        case 2 : viewCustomerByCustomerId(); return;
    	        case 3 : updateCustomerInfo(); return;
    	        case 4 : deleteCustomer(); return;
    	        case 0 : {
    	            return;
    	        }
    	        default : System.out.println("Invalid choice. Try again.");
    	    }
    	}
        
    }
    
    //CREATE NEW ACCOUNT
    private static void createNewAccount() {
        Account account = new Account();
        System.out.print("Enter Customer ID: ");
        account.setCustomerId(scanner.nextInt());
        System.out.print("Enter Initial Balance: ");
        account.setBalance(scanner.nextDouble());
        System.out.print("Enter Account Type (savings/checking): ");
        account.setType(scanner.next());

        try {
            service.createAccount(account);
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
    
    //VIEW ACCOUNT BY ACCOUNT NUMBER
	private static void viewAccountByAccNumber() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        try {
        	service.viewAccountDetailsByAccountNumber(accountNumber);
            
        } catch (SQLException e) {
            System.out.println("Error fetching account details: " + e.getMessage());
        }
		
	}
	
	//UPDATE ACCOUNT USING ACCOUNT NUMBER
	private static void updateAccountInfo() {
		System.out.print("Enter Account Number of Account to be Updated: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter New Customer ID: ");
        int newCustomerID = scanner.nextInt();
        System.out.print("Enter New Balance: ");
        double newBalance = scanner.nextDouble();
        System.out.print("Enter New Account Type (savings/checking): ");
        String newType = (scanner.next());
        try {
        	service.updateAccountDetailsByAccountNumber(accountNumber, newCustomerID, newBalance, newType);
            
        } catch (SQLException e) {
            System.out.println("Error updating account details: " + e.getMessage());
        }
		
	}

	//DELETE ACCOUNT USING ACCOUNT NUMBER
	private static void closeAccount() {
		System.out.print("Enter Account Number of Account to be Closed: ");
        int accountNumber = scanner.nextInt();
        service.deleteAccountByNumber(accountNumber);
	}
	
	//CREATE NEW CUSTOMER
    private static void createNewCustomer() {
        Customer customer = new Customer();
        System.out.print("Enter First Name: ");
        customer.setName(scanner.next());
        System.out.print("Enter Email: ");
        customer.setEmail(scanner.next());
        System.out.print("Enter 10 Digit Phone Number: ");
        customer.setPhoneNumber(scanner.next());
        System.out.print("Enter Address: ");
        customer.setAddress(scanner.next());

        try {
            service.createCustomer(customer);
            System.out.println("Customer created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }
    
    //VIEW CUSTOMER BY CUSTOMER ID
    private static void viewCustomerByCustomerId() {
        System.out.print("Enter Customer Id: ");
        int customerId = scanner.nextInt();
        try {
        	service.viewCustomerDetailsByCustomerId(customerId);
            
        } catch (SQLException e) {
            System.out.println("Error fetching customer details: " + e.getMessage());
        }
		
	}
    
    //UPDATE CUSTOMER USING CUSTOMER ID
  	private static void updateCustomerInfo() {
  		System.out.print("Enter Customer Id of Customer to be Updated: ");
          int customerId = scanner.nextInt();
          System.out.print("Enter New Name for the Customer: ");
          String newName = scanner.next();
          System.out.print("Enter New Email for the Customer: ");
          String newEmail = scanner.next();
          System.out.print("Enter New Phone Number for the Customer: ");
          String newPhoneNumber = scanner.next();
          System.out.print("Enter New Address for the Customer: ");
          String newAddress = scanner.next();
          try {
          	service.updateCustomerDetailsByCustomerId(customerId,newName,newEmail,newPhoneNumber,newAddress);
              
          } catch (SQLException e) {
              System.out.println("Error updating customer details: " + e.getMessage());
          }
  		
  	}

    //DELETE CUSTOMER USING CUSTOMER ID
  	private static void deleteCustomer() {
  		System.out.print("Enter Customer Id of Customer to be Deleted: ");
          int customerId = scanner.nextInt();
          service.deleteCustomerById(customerId);
  	}
  	
  	//DEPOSIT FUNDS
    private static void depositFunds() {
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter Amount to Deposit: ");
        double amount = scanner.nextDouble();

        try {
            service.deposit(accountNumber, amount);
            System.out.println("Deposit successful.");
        } catch (SQLException e) {
            System.out.println("Error depositing funds: " + e.getMessage());
        }
    }
    
    //WITHDRAW FUNDS
    private static void withdrawFunds() {
    	System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        boolean success = service.withdrawFunds(accountNumber, amount);
        if (success) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Please check the account number or balance.");
        }
	}
    
    //TRANSFER FUNDS
    private static void transferFunds() {
    	System.out.print("Enter source account number: ");
        int sourceAccountNumber = scanner.nextInt();
        System.out.print("Enter destination account number: ");
        int destinationAccountNumber = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double transferAmount = scanner.nextDouble();

        boolean transferSuccess = service.transferFunds(sourceAccountNumber, destinationAccountNumber, transferAmount);
        if (transferSuccess) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Please check account numbers or balance.");
        }
    }

    //VIEW TRANSACTION HISTORY
    private static void viewTransactionHistory() {
    	System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        List<Transaction> transactions = service.getTransactionHistory(accountNumber);
        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("No transactions found for this account.");
        }
	}

}

