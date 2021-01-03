package exception;

public class SpringArcanaException extends RuntimeException {
    public SpringArcanaException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public SpringArcanaException(String exMessage) {
        super(exMessage);
    }

}        