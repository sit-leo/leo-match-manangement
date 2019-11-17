package app.leo.matchmanagement.exceptions;

public class WrongOrganizationException extends HttpException{

    public WrongOrganizationException() {
    }

    public WrongOrganizationException(String message) {
        super(message);
    }

    public WrongOrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongOrganizationException(Throwable cause) {
        super(cause);
    }

    public WrongOrganizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
