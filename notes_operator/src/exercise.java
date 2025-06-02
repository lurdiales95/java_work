public class exercise {
        public static void main(String[] args) {

            double loanAmount = 250000.00;
            double annualInterestRate = 5.5;
            int loanTermsYears = 5;
            double monthlyPayment;

            System.out.println("\nAssignment Operators");
            monthlyPayment = (loanAmount * (annualInterestRate / 100)) / 12;
            System.out.printf("Monthly Payment: $%.2f", monthlyPayment);

            loanAmount += 5000;
            System.out.printf("\nNew Loan Amount: $%.2f",  loanAmount);

            annualInterestRate = annualInterestRate -= 1;
            System.out.printf("\nNew Annual Interest: %.1f%%", annualInterestRate);

            loanTermsYears ++;
            System.out.println("\nNew Loan Term Year Agreement: " + loanTermsYears);

            System.out.println("\nComparison Operators");
            boolean loanAmountResult;
            loanAmountResult = loanAmount > 30000;
            System.out.println("Loan is greater than $30,000.00: " + loanAmountResult);

            boolean monthlyPaymentResult;
            monthlyPaymentResult = monthlyPayment > 500;
            System.out.println("Monthlay payment is greater than $500.00: " + monthlyPaymentResult);

            System.out.println("\nComparison Operators");
            boolean isAffordable;
            isAffordable = monthlyPayment < 500 && loanTermsYears > 3;
            System.out.println("Monthly payment is affordable: " + isAffordable);

            boolean isExpensive;
            isExpensive = monthlyPayment > 700 || annualInterestRate > 3;
            System.out.println("Monthly payment is expensive: " + isExpensive);

        }


    }

