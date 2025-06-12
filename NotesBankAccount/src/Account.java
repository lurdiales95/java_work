public class Account {
    protected double balance;
    protected String accountNumber;

    public Account(double balance, String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {this.balance += amount;}

    public boolean withdraw(double amount) {
        this.balance -= amount;
        return true;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }

}

// Inheritance is used when many operations have basic content, but need extra. It's meant to reduce duplication in code.