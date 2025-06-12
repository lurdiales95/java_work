import java.util.Random;

public class LockerService {
    private Locker[] lockers;
    private Random random = new Random ();

    // Constructor: initialize lockers with numbers starting from 1
    public LockerService(int numberOfLockers) {
        lockers = new Locker[numberOfLockers];
        for (int i = 0; i < lockers.length; i++) {
            lockers[i] = new Locker(i + 1);
        }
    }
    // Rent the next available locker
    public Result rentLocker() {
        for (Locker locker : lockers) {
            if (!locker.isRented()) {
                locker.setPinCode(generatePinCode());
                String message = String.format("Locker %d rented with pin %s", locker.getLockerNumber(), locker.getPinCode());
                return new Result(true, message);
            }

        }
        return new Result(false, "All lockers are currently rented.");
    }
    // generates pinCode
    private String generatePinCode() {
        int generator = random.nextInt(10000);
        return String.format("%04d", generator);
    }

    // Access a locker using locker number and pin code
    public Result accessLocker(int lockerNumber, String pinCode) {
        if (lockerNumber < 1 || lockerNumber > lockers.length) {
            return new Result(false, "That locker number is invalid.");
        }
        Locker locker = lockers[lockerNumber - 1];

        if (!locker.isRented()) {
            return new Result(false, "That locker is not rented!");
        }

        if (!locker.getPinCode().equals(pinCode)) {
            return new Result(false, "Incorrect pin code provided.");
        }
        return new Result(true, "You have opened the locker.",
                String.valueOf(locker.getLockerNumber()), locker.getPinCode());
    }
    // Release a locker after verifying locker number and pin code
    public Result releaseLocker(int lockerNumber, String pinCode) {
        if (lockerNumber < 1 || lockerNumber > lockers.length) {
            return new Result(false, "That locker number is invalid.");
        }
        Locker locker = lockers[lockerNumber - 1];

        if (!locker.isRented()) {
            return new Result(false, "That locker is not rented!");
        }
        if (!locker.getPinCode().equals(pinCode)) {
            return new Result(false, "Incorrect pin code!");
        }
        locker.releaseLocker();

        return new Result(true, "Locker released successfully.",
                String.valueOf(locker.getLockerNumber()), pinCode);
    }
}