package Bank_Management.myBank.dao;
import Bank_Management.myBank.model.Account;
import Bank_Management.myBank.util.DatabaseConnection;

import java.sql.*;


    public  class AccountDAOImpl implements AccountDAO {



        @Override
        public  int debit(String accountNumber, double amount, Connection conn) throws SQLException {
            // এই SQL কোয়েরিটি অ্যাটমিকভাবে ব্যালেন্স চেক করে এবং টাকা ডেবিট করে
            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";

            try (PreparedStatement debitStmt = conn.prepareStatement(debitSql)) {
                debitStmt.setDouble(1, amount);
                debitStmt.setString(2, accountNumber);
                debitStmt.setDouble(3, amount); // ব্যালেন্স যথেষ্ট আছে কিনা তা নিশ্চিত করা হয়
                return debitStmt.executeUpdate(); // প্রভাবিত হওয়া সারির সংখ্যা (0 বা 1) ফেরত দেয়
            }
        }

        @Override
        public int credit(String accountNumber, double amount, Connection conn) throws SQLException {
            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

            try (PreparedStatement creditStmt = conn.prepareStatement(creditSql)) {
                creditStmt.setDouble(1, amount);
                creditStmt.setString(2, accountNumber);
                return creditStmt.executeUpdate(); // প্রভাবিত হওয়া সারির সংখ্যা (0 বা 1) ফেরত দেয়
            }
        }


        @Override
        public void saveAccount(Account account) {
            String sql = "INSERT INTO accounts (account_number, customer_id, balance) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, account.getAccountNumber());
                pstmt.setInt(2, account.getCustomerId());
                pstmt.setDouble(3, account.getBalance());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Account findAccountByNumber(String accountNumber) {
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            Account account = null;
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, accountNumber);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    account = new Account();
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setBalance(rs.getDouble("balance"));
                    account.setDateOpened(rs.getTimestamp("date_opened"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return account;
        }


        }


