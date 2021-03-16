package com.c4nn4.pix_engine.manager.network.message;

public class NetMessage<T extends Messageable> {

    private final int id;
    private int size;

    NetMessage(T id) {
        this(id.getType());
    }

    NetMessage(int id) {
        this.id = id;
        this.size = 0;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addSize(int size) {
        this.size += size;
    }
}
