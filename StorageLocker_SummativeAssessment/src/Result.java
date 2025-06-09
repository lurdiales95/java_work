public class Result {
    private final boolean success;
    private final String message;
    private final String lockerNumber;
    private final String pinCode;

    //When there is no pinCode and the locker hasn't been assigned
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.pinCode = null;
        this.lockerNumber = null;
    }

    //When locker has been assigned
    public Result(boolean success, String message, String pinCode) {
        this.success = success;
        this.message = message;
        this.pinCode = pinCode;
        this.lockerNumber = null;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public String getPinCode(){
        return pinCode;
    }
}
