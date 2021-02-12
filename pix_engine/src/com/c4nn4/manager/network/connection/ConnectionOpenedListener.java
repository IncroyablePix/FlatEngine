package com.c4nn4.manager.network.connection;

import java.net.Socket;

public interface ConnectionOpenedListener {
    void onConnected(Socket socket);
}
