package com.c4nn4.manager.network.connection;

public interface ConnectionInitializer {
    void connect(String ip, int port, ConnectionOpenedListener listener);
}
