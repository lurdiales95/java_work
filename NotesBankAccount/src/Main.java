import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Account> myaccounts = new ArrayList<>();

        // boxing
        myaccounts.add(new SavingsAccount(100, "S1234", 0.015));
        myaccounts.add(new CheckingAccount(500, "C1234"));

        System.out.println("Account | Balance");

        for (int i = 0; i < myaccounts.size(); i++) {
            System.out.printf("%s | %f%n", myaccounts.get(i).getAccountNumber(), myaccounts.get(i).getBalance());
        }

    }
}
           /* if (myaccounts.get(i) instanceof SavingsAccount) {

            }
                // unboxing (casting - cast object as a savings account)
          SavingsAccount acct = (SavingsAccount) myaccounts.get(i);
                acct.calculateInterest();

               System.out.println(acct.getBalance());
            } else {
                System.out.printf("%s is not a savings account!%n", myaccounts.get(i).getAccountNumber());
            }
        }

    }
}
*/