package Bank_Management.myBank.model;

import java.sql.Timestamp;

public class Account {
    private String accountNumber;
    private int customerId;
    private double balance;
    private Timestamp dateOpened;

    // Constructors, Getters, and Setters
    public Account() {}

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public Timestamp getDateOpened() { return dateOpened; }
    public void setDateOpened(Timestamp dateOpened) { this.dateOpened = dateOpened; }
}