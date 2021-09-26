package com.c4nn4.pix_engine.manager.network.connection;

import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.Messageable;
import com.c4nn4.pix_engine.manager.network.message.NetQueue;
import com.c4nn4.pix_engine.manager.network.message.OwnedMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public abstract class Client<T extends Messageable> {
    private final NetQueue<OwnedMessage<T>> toRead;

    private Thread thread;
    private Connection<T> connection;

    private boolean running;

    public Client() {
        this.toRead = new NetQueue<>();
        this.connection = new Connection<>(Owner.Client, toRead, new NetQueue<>());
        this.running = false;
    }

    public void connect(String address, int port) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(address, port);
            connection.connectToServer(socketAddress);
            thread = new Thread(connection);
            thread.start();
        }
        catch (IOException e) {
            onConnectionFailed();
        }

        onConnected();
    }

    public void disconnect() {
        if(isConnected())
            connection.disconnect();

        running = false;

        try {
            this.thread.interrupt();
            this.thread.join();
        }
        catch (InterruptedException ignored) { }

        onDisconnect(Disconnection.DISCONNECTED);
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    public void send(Message<T> message) {
        if(isConnected())
            connection.send(message);
    }

    public NetQueue<OwnedMessage<T>> incoming() {
        return this.toRead;
    }

    protected abstract void onDisconnect(Disconnection type);
    protected abstract void onConnected();
    protected abstract void onConnectionFailed();
}
