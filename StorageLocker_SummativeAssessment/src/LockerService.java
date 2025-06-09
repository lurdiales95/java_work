public class LockerService {
    private Locker[] lockers;

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
                return locker.rentLocker();
            }
        }
        return new Result(false, "All lockers are currently rented.");
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






/*if locker has a pin, then it's rented. If pin is null, it's not in use. OR you can create a boolean variable
isRented */

/* WHAT WE NEED TO WORK ON IS GETTING FROM public Result accessLocker(int lockerNumber, String pinNumber)
TO INDEX */


//do what it takes to rent the locker
// give them the next available locker
//there will be a point where the lockers are full
//find available locker => new result with pin *if (true) below