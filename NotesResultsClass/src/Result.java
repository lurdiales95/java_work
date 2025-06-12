public class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    // this is how we "print" an object with its data instead of the memory address
    @Override
    public String toString() {
        return String.format("Result {success:%s, message=%s, data=%s}", success, message, data);
    }
}

