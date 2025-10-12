package Bank_Management.myBank.dao;

import Bank_Management.myBank.model.Customer;
import Bank_Management.myBank.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer saveCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, last_name, user_name, password_encrypted) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getUsername());
            pstmt.setString(4, customer.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCustomerId(generatedKeys.getInt(1));
                    }
                }
            }
            return customer;
        }
    }

    @Override
    public Customer findCustomerByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM customers WHERE user_name = ?";
        Customer customer = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setUsername(rs.getString("user_name"));
                customer.setPassword(rs.getString("password_encrypted"));
            }
        }
        return customer;
    }
}