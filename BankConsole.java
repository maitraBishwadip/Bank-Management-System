package Bank_Management.myBank.view;





import Bank_Management.myBank.model.Customer;
import Bank_Management.myBank.service.AccountService;
import Bank_Management.myBank.model.Account;
import Bank_Management.myBank.service.CustomerService;

import java.util.Scanner;

public class BankConsole {
    private AccountService accountService = new AccountService();
    private CustomerService customerService = new CustomerService();
    private Scanner scanner = new Scanner(System.in);
    private Customer loggedInCustomer = null; // Tracking if already logged in

    public void start() {
        while (true) {
            if (loggedInCustomer == null) {
                showPreLoginMenu();
            } else {
                showPostLoginMenu();
            }
        }
    }

    private void showPreLoginMenu() {
        System.out.println("\n--- Welcome to Simple Bank ---");
        System.out.println("1. Login");
        System.out.println("2. Register New Customer");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: login(); break;
            case 2: register(); break;
            case 3: System.out.println("Exiting application."); System.exit(0);
            default: System.out.println("Invalid option. Please try again.");
        }
    }

    private void showPostLoginMenu() {
        System.out.println("\n--- Welcome, " + loggedInCustomer.getFirstName() + "! ---");
        System.out.println("1. Create New Account");
        System.out.println("2. View Account Details");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: createAccount(); break;
            case 2: viewAccount(); break;
            case 3: deposit(); break;
            case 4: withdraw(); break;
            case 5: transfer(); break;
            case 6: logout(); break;
            default: System.out.println("Invalid option. Please try again.");
        }
    }

    private void register() {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Choose a Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Choose a Password: ");
        String password = scanner.nextLine().trim();

        // *** মূল পরিবর্তনটি এখানে ***
        // রেজিস্ট্রেশনের সময় প্রাথমিক জমার পরিমাণ ইনপুট নেওয়া হচ্ছে
        System.out.print("Enter Initial Deposit Amount to create your first account: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setUsername(username);
        newCustomer.setPassword(password);

        // *** এবং এখানে ***
        // এখন দুটি আর্গুমেন্টই সঠিকভাবে পাস করা হচ্ছে
        if (customerService.register(newCustomer, initialBalance)) {
            System.out.println("Registration successful! A new bank account has been created for you. Please login.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private void login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        loggedInCustomer = customerService.login(username, password);

        if (loggedInCustomer != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void logout() {
        loggedInCustomer = null;
        System.out.println("You have been logged out.");
    }

    private void createAccount() {
        // এখন আর কাস্টমার আইডি জিজ্ঞেস করার দরকার নেই, কারণ আমরা জানি কে লগইন করা আছে
        System.out.println("DEBUG: Passing Customer ID to service: " + loggedInCustomer.getCustomerId());

        System.out.print("Enter Initial Deposit Amount: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Account newAccount = accountService.createAccount(loggedInCustomer.getCustomerId(), balance);
        if (newAccount != null) {
            System.out.println("Account created successfully!");
            System.out.println("Your new Account Number is: " + newAccount.getAccountNumber());
        } else {
            System.out.println("Account creation failed.");
        }
    }

    private void viewAccount() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        Account account = accountService.getAccountDetails(accNum);
        if (account != null) {
            System.out.println("--- Account Details ---");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Customer ID: " + account.getCustomerId());
            System.out.println("Current Balance: " + account.getBalance());
            System.out.println("Date Opened: " + account.getDateOpened());
        } else {
            System.out.println("Account not found.");
        }
    }

    private void deposit() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Amount to Deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        // accountService.deposit(accNum, amount); // You need to implement deposit in AccountService
    }

    private void withdraw() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Amount to Withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        // accountService.withdraw(accNum, amount); // You need to implement withdraw in AccountService
    }

    private void transfer() {
        System.out.print("Enter Your Account Number (From): ");
        String fromAcc = scanner.nextLine();
        System.out.print("Enter Recipient's Account Number (To): ");
        String toAcc = scanner.nextLine();
        System.out.print("Enter Amount to Transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        accountService.transferMoney(fromAcc, toAcc, amount);
    }
}
