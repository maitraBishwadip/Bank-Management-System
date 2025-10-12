package Bank_Management.myBank.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bank_db"; // Make sure your port is correct
    private static final String USER = "root"; // আপনার ইউজারনেম দিন
    private static final String PASSWORD = "123456"; // আপনার পাসওয়ার্ড দিন

    // private constructor-এর আর প্রয়োজন নেই
    // public DatabaseConnection() {}

    /**
     * This method now creates and returns a new connection every time it's called.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // প্রতিবার একটি নতুন কানেকশন তৈরি করে ফেরত দেবে
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // This is a more critical error, so we can wrap it in a RuntimeException
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }
}