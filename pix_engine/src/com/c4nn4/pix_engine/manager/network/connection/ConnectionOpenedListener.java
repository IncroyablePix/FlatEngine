package com.c4nn4.pix_engine.manager.network.connection;

import java.net.Socket;

public interface ConnectionOpenedListener {
    void onConnected(Socket socket);
}
