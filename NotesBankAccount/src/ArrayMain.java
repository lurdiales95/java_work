import java.util.ArrayList;

public class ArrayMain {
    public static void  main(String[] args) {
        ArrayList<Account> myaccounts = new ArrayList<>();

        // boxing
        myaccounts.add(new SavingsAccount(100, "S1234", 0.015));
        myaccounts.add((new CheckingAccount(500, "C1234"));

        for (int i = 0; i < myaccounts.size(); i++) {
            if (myaccounts.get(i) instanceof SavingsAccount) {
            // unboxing
            SavingsAccount acct = (SavingsAccount) myaccounts.get(i);
            acct.calculateInterest();

                System.out.println(acct.getBalance());

            } else {
                System.out.printf("%s is not a savings account!%n", myaccounts.get(i).getAccountNumber());
            }
        }
    }

}
