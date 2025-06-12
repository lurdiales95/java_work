public class Main {
    public static void main(String[] args) {
        // Ask user how many lockers aare available
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