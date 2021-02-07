package be.helmo.manager.network.connection;

import java.io.IOException;
import java.net.Socket;

public class HigherGroundsConnectionInitializer implements ConnectionInitializer {
    private final static int MAX_ATTEMPTS = 5;

    @Override
    public void connect(String ip, int port, ConnectionOpenedListener listener) {
        new Thread(() -> {
            int attempts = 0;
            Socket socket = null;

            while (attempts < MAX_ATTEMPTS && socket == null) {
                try {
                    socket = new Socket(ip, port);
                }
                catch (IOException e) {
                    attempts++;
                }
            }

            listener.onConnected(socket);
        }).start();
    }
}
