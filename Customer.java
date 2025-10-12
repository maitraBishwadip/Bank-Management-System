package Bank_Management.myBank.model;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password; // Plain text password, will be encrypted by Bank_Management.myBank.dao.service

    // Constructors, Getters, and Setters
    public Customer() {}

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}