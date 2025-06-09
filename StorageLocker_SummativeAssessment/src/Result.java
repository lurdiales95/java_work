public class Result {
    private final boolean success;
    private final String message;
    private final String lockerNumber;
    private final String pinCode;

    // When there is no pinCode and the locker hasn't been assigned
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.lockerNumber = null;
        this.pinCode = null;
    }

    // When both Locker and PIN code are both present
    public Result(boolean success, String message, String lockerNumber, String pinCode) {
        this.success = success;
        this.message = message;
        this.lockerNumber = lockerNumber;
        this.pinCode = pinCode;

    }
    // When only pin code is provided
    public Result(boolean success, String message, String pinCode) {
        this.success = success;
        this.message = message;
        this.pinCode = pinCode;
        this.lockerNumber = null;
    }

    // Getters
    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public String getPinCode(){
        return pinCode;
    }

    public String getLockerNumber() {
        return lockerNumber;

    }
}
