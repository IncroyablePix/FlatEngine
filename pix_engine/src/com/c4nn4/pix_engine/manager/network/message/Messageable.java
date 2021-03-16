package com.c4nn4.pix_engine.manager.network.message;

public interface Messageable {
    int getType();

    static Messageable getHeader(int val) {
        return null;
    }
}
