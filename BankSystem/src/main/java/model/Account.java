package model;

public class Account {
	private int accountNumber;
    private int customerId;
    private double balance;
    private String type;
	public Account(int accountNumber, int customerId, double balance, String type) {
		super();
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.balance = balance;
		this.type = type;
	}
	public Account() {
		super();
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", customerId=" + customerId + ", balance=" + balance
				+ ", type=" + type + "]";
	}

    
}
