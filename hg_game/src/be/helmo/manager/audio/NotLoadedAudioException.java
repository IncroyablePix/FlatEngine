package be.helmo.manager.audio;

public class NotLoadedAudioException extends RuntimeException {
    public NotLoadedAudioException() {
        super();
    }

    public NotLoadedAudioException(String msg) {
        super(msg);
    }

    public NotLoadedAudioException(Exception e) {
        super(e);
    }

    public NotLoadedAudioException(String msg, Exception e) {
        super(msg, e);
    }
}
