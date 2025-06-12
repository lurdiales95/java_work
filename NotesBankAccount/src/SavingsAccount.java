public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, String accountNumber, double interestRate) {
        super(balance, accountNumber);
        this.interestRate = interestRate;
    }

    public void calculateInterest() {
        this.balance *= (1 + interestRate);

    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount < 0) {
            return false;
        }

       return super.withdraw(amount);

    }
}
