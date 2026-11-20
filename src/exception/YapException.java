package yapspace.exception;

public class YapException extends RuntimeException {

    public YapException(String message) {
        super(message);
    }

    public YapException(String message, Throwable cause) {
        super(message, cause);
    }
}
