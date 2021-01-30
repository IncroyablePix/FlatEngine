package be.helmo.manager.image;

public class SpriteNotFoundException extends ImageNotFoundException {
    public SpriteNotFoundException() {
    }

    public SpriteNotFoundException(String message) {
        super(message);
    }

    public SpriteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpriteNotFoundException(Throwable cause) {
        super(cause);
    }

    public SpriteNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
