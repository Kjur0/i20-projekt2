package edu.tm1.krzysztof.jurkowski.i20.projekt2;

@SuppressWarnings("unused")
public class NotReadyException extends Exception {
    public NotReadyException() {
        super();
    }

    public NotReadyException(String message) {
        super(message);
    }

    public NotReadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotReadyException(Throwable cause) {
        super(cause);
    }

    protected NotReadyException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
