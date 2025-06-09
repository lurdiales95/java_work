public class Main {
    public static void main(String[] args) {
        // Asks user how many lockers aare available
        int numberOfLockersAvailable = IO.askForNumberOfLockersAvailable();

        // Initiates the LockerService
        LockerService service = new LockerService(numberOfLockersAvailable);


        boolean running = true;

        while (running) {
            int choice = IO.getMenuChoices();

            switch (choice) {

                // Rent locker
                case 1:
                    Result rentResult = service.rentLocker();
                    IO.displayResult(rentResult);
                    break;

                // Access locker
                case 2:
                    int accessNumber = IO.getLockerNumber();
                    String accessPin = IO.getPinCode();
                    Result accessResult = service.accessLocker(accessNumber, accessPin);
                    IO.displayResult(accessResult);
                    break;

                // Release a locker
                case 3: // Release a locker
                    int releaseLockerNumber = IO.getLockerNumber();
                    String releasePinCode = IO.getPinCode();
                    Result releaseResult = service.releaseLocker(releaseLockerNumber, releasePinCode);
                    IO.displayResult(releaseResult);
                    break;

                // Exit service
                case 4:
                    System.out.println("Thank you for using the locker rental system!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


    /* RULES
    1. You may only use the Scanner and System.out.println() in one class
    2. Other classes must return strings, booleans, etc. so you know what to print
    3. What classes should you have:
        1. Locker
        2. LockerService (rent, access, release)
        3. IO|Utilities (printing scanner)
        4. Main (app workflow)
        5. Result


     */

//Main should:
//Spit out locker service,
//Configure array
//Make sure everything is ready to run
//Start a loop on main to display the menu. Ask IO class to display menu, get user choice, and display prompts.