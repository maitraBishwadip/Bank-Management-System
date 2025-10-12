package Bank_Management.myBank.service;

import Bank_Management.myBank.dao.CustomerDAO;
import Bank_Management.myBank.dao.CustomerDAOImpl;
import Bank_Management.myBank.model.Customer;
import Bank_Management.myBank.util.DataEncryption;
import Bank_Management.myBank.util.DatabaseConnection;
import java.sql.SQLException;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private AccountService accountService = new AccountService();

    public boolean register(Customer customer, double initialBalance) {
        try {
            // Check if username already exists
            if (customerDAO.findCustomerByUsername(customer.getUsername()) != null) {
                System.out.println("Username already exists. Please choose another one.");
                return false;
            }

            String plainPassword = customer.getPassword();
            String encryptedPassword = DataEncryption.encrypt(plainPassword);
            customer.setPassword(encryptedPassword);

            Customer savedCustomer = customerDAO.saveCustomer(customer);

            if (savedCustomer != null && savedCustomer.getCustomerId() > 0) {
                // Create an initial account for the new customer
                accountService.createAccount(savedCustomer.getCustomerId(), initialBalance);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during registration process: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Customer login(String username, String plainPassword) {
        try {
            Customer customer = customerDAO.findCustomerByUsername(username);

            if (customer != null) {
                String storedEncryptedPassword = customer.getPassword();
                String enteredEncryptedPassword = DataEncryption.encrypt(plainPassword);

                if (storedEncryptedPassword.equals(enteredEncryptedPassword)) {
                    return customer; // Login successful
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login process: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Login failed
    }
}