public class account {

    private double balance;
    public String accountNumber;

    public account(String number, double balance) {
        this.accountNumber = number;
        this.balance = balance;
    }
    public void Deposit(double amount) {
        this.balance += amount;
    }
//encapsulation, "data hiding"
    public double Withdraw(double amount) {
        //do they have neough funds?
        if (amount > this.balance){
            return 0;
        }

        // ok deduct the amount and return the new balance
        balance -= amount;
        return balance;
    }
}
