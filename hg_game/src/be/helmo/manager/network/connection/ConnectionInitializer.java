package be.helmo.manager.network.connection;

public interface ConnectionInitializer {
    void connect(String ip, int port, ConnectionOpenedListener listener);
}
