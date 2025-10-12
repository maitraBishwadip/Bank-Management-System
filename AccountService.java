package Bank_Management.myBank.service;



import Bank_Management.myBank.dao.AccountDAO;
import Bank_Management.myBank.dao.AccountDAOImpl;

import Bank_Management.myBank.model.Account;
import Bank_Management.myBank.util.DatabaseConnection;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAOImpl();

    public Account createAccount(int customerId, double initialBalance) {
        // এই মেথড অপরিবর্তিত
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setCustomerId(customerId);
        account.setBalance(initialBalance);
        // accountDAO.saveAccount(account); // You would need to implement this method in the DAO
        return account;
    }

    public Account getAccountDetails(String accountNumber) {
        // এই মেথড অপরিবর্তিত
        // return accountDAO.findAccountByNumber(accountNumber); // You would need to implement this
        return null; // Placeholder
    }

    // deposit and withdraw methods can also be updated to use the new DAO methods for better consistency.

    /**
     * Atomically transfers money from one account to another using separate debit and credit methods.
     * This method ensures ACID properties.
     */
    public boolean transferMoney(String fromAccNum, String toAccNum, double amount) {
        // 1. try-with-resources ব্যবহার করে কানেকশন নেওয়া হয়েছে, যা স্বয়ংক্রিয়ভাবে বন্ধ হয়ে যাবে
        try (Connection conn = DatabaseConnection.getConnection()) {

            // 2. ট্রানজ্যাকশন শুরু করার জন্য auto-commit বন্ধ করা হলো
            conn.setAutoCommit(false);

            // 3. DAO থেকে debit এবং credit মেথড কল করা হচ্ছে
            int debitRowsAffected = accountDAO.debit(fromAccNum, amount, conn);
            int creditRowsAffected = accountDAO.credit(toAccNum, amount, conn);

            // 4. দুটি অপারেশন সফল হয়েছে কিনা তা যাচাই করা হচ্ছে
            if (debitRowsAffected == 1 && creditRowsAffected == 1) {
                // যদি ডেবিট এবং ক্রেডিট উভয়ই সফলভাবে একটি করে সারি প্রভাবিত করে, তাহলে ট্রানজ্যাকশন commit করো
                conn.commit();
                System.out.println("Transfer successful!");
                return true;
            } else {
                // যদি কোনো একটি অপারেশন ব্যর্থ হয় (যেমন: অপর্যাপ্ত ব্যালেন্স বা ভুল অ্যাকাউন্ট নম্বর),
                // তাহলে সম্পূর্ণ ট্রানজ্যাকশনটি rollback করো।
                conn.rollback();
                System.out.println("Transfer failed. Insufficient balance or invalid account number.");
                return false;
            }

        } catch (SQLException e) {
            // ডেটাবেসে কোনো সমস্যা হলে SQLException 발생 করবে
            System.err.println("Transaction failed due to a database error: " + e.getMessage());
            // একটি catch ব্লকের মধ্যে rollback করা একটি ভালো অভ্যাস, যদিও কানেকশন বন্ধ হয়ে গেলে এটি স্বয়ংক্রিয়ভাবে হয়
            return false;
        }
    }
}