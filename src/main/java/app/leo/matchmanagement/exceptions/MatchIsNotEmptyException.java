package app.leo.matchmanagement.exceptions;

public class MatchIsNotEmptyException extends HttpException {

    public MatchIsNotEmptyException() {
    }

    public MatchIsNotEmptyException(String message) {
        super(message);
    }

    public MatchIsNotEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchIsNotEmptyException(Throwable cause) {
        super(cause);
    }

    public MatchIsNotEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
