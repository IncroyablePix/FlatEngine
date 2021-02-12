package com.c4nn4.manager.audio;

public class AudioLoadException extends RuntimeException {
    public AudioLoadException() {
    }

    public AudioLoadException(String message) {
        super(message);
    }

    public AudioLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioLoadException(Throwable cause) {
        super(cause);
    }

    public AudioLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
