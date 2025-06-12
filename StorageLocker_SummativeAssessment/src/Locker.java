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

    public int getLockerNumber() {
        return lockerNumber;
    }

    public void releaseLocker() {
        this.pinCode = null;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}


//make the locker class as simplistic as possible and the locker service more complex. All getters and setters.