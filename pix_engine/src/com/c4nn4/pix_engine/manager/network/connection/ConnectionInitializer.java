package com.c4nn4.pix_engine.manager.network.connection;

public interface ConnectionInitializer {
    void connect(String ip, int port, ConnectionOpenedListener listener);
}
