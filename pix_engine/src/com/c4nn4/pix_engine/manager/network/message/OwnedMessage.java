package com.c4nn4.pix_engine.manager.network.message;

import com.c4nn4.pix_engine.manager.network.connection.Connection;

public class OwnedMessage<T extends Messageable> {
    private Connection<T> remote;
    private Message<T> message;

    public OwnedMessage(Connection<T> remote, Message<T> message) {
        this.remote = remote;
        this.message = message;
    }

    public Connection<T> getRemote() {
        return remote;
    }

    public Message<T> getMessage() {
        return message;
    }

    public int getSize() { return message.header()[1]; }

    public int getMessageId() { return message.header()[0]; }

    @Override
    public String toString() {
        return message.toString();
    }
}
