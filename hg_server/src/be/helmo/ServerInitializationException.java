package be.helmo;

public class ServerInitializationException extends RuntimeException {
    public ServerInitializationException() { super(); }
    public ServerInitializationException(String msg) { super(msg); }
    public ServerInitializationException(Exception e) { super(e); }
    public ServerInitializationException(String msg, Exception e) { super(msg, e); }
}
