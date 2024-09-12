package model;
import java.sql.Timestamp;
public class Transaction {
	private int transactionId;
    private int accountNumber;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    
	public Transaction(int transactionId, int accountNumber, String transactionType, double amount,
			Timestamp transactionDate) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public Transaction() {
		super();
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", transactionType="
				+ transactionType + ", amount=" + amount + ", transactionDate=" + transactionDate + "]";
	}

    
}
