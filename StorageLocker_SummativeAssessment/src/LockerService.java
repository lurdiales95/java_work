public class LockerService {
    private Locker[] lockers;

    public LockerService(int numberOfLockers) {
        lockers = new Locker[numberOfLockers];

        for (int i = 0; i < lockers.length; i++) {
            lockers[i] = new Locker();
        }
    }

    public Result rentlocker() {
        //do what it takes to rent the locker
        // give them the next available locker
        //there will be a point where the lockers are full
        //find available locker => new result with pin *if (true) below
        if (true) {
            return new Result(true, "You have rented the locker, here's your pin:");
        } else {
            return new Result(false, "All the lockers are full!");

        }

    }

    public Result accessLocker(int lockerNumber, String pinNumber) {
        //try to access the locker
        return new Result(true, "You have opened the locker!");

        return new Result(false, "That locker number is invalid.");

        return new Result(false, "Incorrect pin number!");

        return new Result(false, "That locker is not rented!");
    }
}

/*if locker has a pin, then it's rented. If pin is null, it's not in use. OR you can create a boolean variable
isRented */

/* WHAT WE NEED TO WORK ON IS GETTING FROM public Result accessLocker(int lockerNumber, String pinNumber)
TO INDEX */
