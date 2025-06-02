import java.util.Scanner;

public class PasswordPassword {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;

        System.out.println("Enter the password: ");

        while (true) {
            password = scanner.nextLine();

            if (password.equals("letmein")) {
                System.out.println(("Access granted!"));
                break;
            } else {
                System.out.println("Wrong password, try again:");
            }
        }

        scanner.close();;
    }
}