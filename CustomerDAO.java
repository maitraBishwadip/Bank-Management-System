package Bank_Management.myBank.dao;

import Bank_Management.myBank.model.Customer;
import java.sql.SQLException;

public interface CustomerDAO {

    /**
     * Saves a new customer and returns the customer object with the new ID.
     * @param customer The Customer object to save.
     * @return The saved customer with the auto-generated ID.
     * @throws SQLException for database errors.
     */
    Customer saveCustomer(Customer customer) throws SQLException;

    /**
     * Finds a customer by their username.
     * @param username The username of the customer to find.
     * @return The Customer object if found, otherwise null.
     * @throws SQLException for database errors.
     */
    Customer findCustomerByUsername(String username) throws SQLException;
}