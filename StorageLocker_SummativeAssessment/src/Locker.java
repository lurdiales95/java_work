import java.util.Random;

public class Locker {
    private final int lockerNumber; // each locker has a number
    private String pinCode; //null in pinCode means the locker is free

    public Locker(int lockerNumber) {
        this.lockerNumber = lockerNumber;
        this.pinCode = null;
    }

    public boolean isRented() {
        return pinCode != null;
    }
    private static final Random random = new Random ();

    public Result rentLocker() {
        if (this.pinCode != null) {
            return new Result(false, "This locker is already taken.");
        }

        int generator = random.nextInt(10000);
        this.pinCode = String.format("%04d", generator);

        return new Result(true, "Locker rented successfully.", String.valueOf(this.lockerNumber), this.pinCode);
    }

    //activate Getters
    public int getLockerNumber() {
        return lockerNumber;
    }

    public void releaseLocker() {
        this.pinCode = null;
    }

    public String getPinCode() {
        return pinCode;
    }
}


//should have locker number and pin
//if locker hasn't been rented the pin number should be null

