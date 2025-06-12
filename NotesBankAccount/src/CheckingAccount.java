public class CheckingAccount extends Account {
    public CheckingAccount(double balance, String accountNumber) {
        super(balance, accountNumber);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount < 0) {
            // Not enough funds, apply overdraft fee
            balance -= 15;
        }
        // Withdraw anyway (this always returns true in your base class)
        return super.withdraw(amount);
    }
}

