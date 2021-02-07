package be.helmo.manager.network.connection;

import java.net.Socket;

public interface ConnectionOpenedListener {
    void onConnected(Socket socket);
}
