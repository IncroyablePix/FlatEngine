package com.c4nn4.network;

public interface ConnectionListener {
    void onConnected();
    void onConnectionFailed();
    void onDisconnect();
}
