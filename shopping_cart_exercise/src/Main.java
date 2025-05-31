public class Main {

    public static void main(String[] args) {

        //Declaring and assigning num variables
        String productID = "1";
        String productCategory = "2";
        double productCost = 2.56;
        double  productPrice = 4.99;
        int productQuantity = 78;

        //calcuating
        double profitMargin =  (productPrice * productQuantity);
        System.out.println("Profit Margin: $" + profitMargin);

        double totalCost = (productCost * productQuantity);
        System.out.println("Total Cost: $" + totalCost);

        double potentialProfit = (profitMargin - totalCost);
        System.out.printf("Potential Profit: $%.2f\n", potentialProfit);


        }
    }
