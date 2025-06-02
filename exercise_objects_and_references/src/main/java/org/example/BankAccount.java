package org.example;

public class BankAccount {
    String accountName;
    double balance;

    public BankAccount (String accountName, double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public void displayBalance() {
        System.out.println("Balance = " + getBalance());

    }

    public void deposit (int deposit) {
        this.balance = getBalance() + deposit;

    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
