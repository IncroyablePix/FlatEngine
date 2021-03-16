package com.c4nn4.pix_engine.manager.network.connection;

public class ConnectionRefusedException extends RuntimeException {
    public ConnectionRefusedException() {
        super();
    }

    public ConnectionRefusedException(String message) {
        super(message);
    }

    public ConnectionRefusedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionRefusedException(Throwable cause) {
        super(cause);
    }

    public ConnectionRefusedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
