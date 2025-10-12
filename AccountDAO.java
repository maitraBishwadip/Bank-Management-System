package Bank_Management.myBank.dao;
import java.sql.Connection;
import java.sql.SQLException;

import Bank_Management.myBank.model.Account;





public interface AccountDAO {

    /**
     * Saves a new account to the database.
     * @param account The Account object to save.
     * @throws SQLException for database errors.
     */
    void saveAccount(Account account) throws SQLException;

    /**
     * Finds an account by its account number.
     * @param accountNumber The account number to find.
     * @return The Account object if found, otherwise null.
     * @throws SQLException for database errors.
     */
    Account findAccountByNumber(String accountNumber) throws SQLException;

    /**
     * Atomically debits a specified amount from an account.
     * @param accountNumber The account to debit from.
     * @param amount The amount to debit.
     * @param conn The transactional connection to use.
     * @return The number of rows affected (1 if successful, 0 if not).
     * @throws SQLException for database errors.
     */
    int debit(String accountNumber, double amount, Connection conn) throws SQLException;

    /**
     * Credits a specified amount to an account.
     * @param accountNumber The account to credit to.
     * @param amount The amount to credit.
     * @param conn The transactional connection to use.
     * @return The number of rows affected (1 if successful, 0 if not).
     * @throws SQLException for database errors.
     */
    int credit(String accountNumber, double amount, Connection conn) throws SQLException;
}

