package com.c4nn4.pix_engine.manager.network.message;

import java.nio.ByteBuffer;

public class Message<T extends Messageable> {
    private static final int CAPACITY = 2048;

    private final NetMessage<T> header;
    private int bufferSize;
    private final ByteBuffer byteBuffer;

    public Message(NetMessage<T> header) {
        this.header = header;
        this.byteBuffer = ByteBuffer.allocate(CAPACITY);
        this.bufferSize = 0;
    }

    public Message(T header) {
        this(new NetMessage<>(header));
    }

    public Message(int header) {
        this(new NetMessage<>(header));
    }

    public int getSize() {
        return this.header.getSize();
    }

    public int[] header() {
        return new int[] { this.header.getId(), this.header.getSize() };
    }

    //---

    public Message<T> add(byte b) {
        this.byteBuffer.put(b);
        this.addSize(1);
        return this;
    }

    public Message<T> add(short s) {
        this.byteBuffer.putShort(s);
        this.addSize(2);
        return this;
    }

    public Message<T> add(int i) {
        this.byteBuffer.putInt(i);
        this.addSize(4);
        return this;
    }

    public Message<T> add(long l) {
        this.byteBuffer.putLong(l);
        this.addSize(8);
        return this;
    }

    public Message<T> add(char c) {
        this.byteBuffer.putChar(c);
        this.addSize(2);
        return this;
    }

    public Message<T> add(float f) {
        this.byteBuffer.putFloat(f);
        this.addSize(4);
        return this;
    }

    public Message<T> add(double d) {
        this.byteBuffer.putDouble(d);
        this.addSize(8);
        return this;
    }

    public Message<T> add(String s) {
        byte[] bytes = s.getBytes();
        this.byteBuffer.put(bytes);
        this.addSize(bytes.length);
        return this;
    }

    private void addSize(int size) {
        this.header.addSize(size);
        if(size > 0)
            bufferSize += size;
    }

    public void setSize(int size) {
        this.header.setSize(size);
    }

    public void resetCursor() {
        this.setSize(bufferSize);
        this.byteBuffer.position(0);
    }

    //---

    public byte getByte() {
        byte b = this.byteBuffer.get();
        this.addSize(-1);
        return b;
    }

    public short getShort() {
        short s = this.byteBuffer.getShort();
        this.addSize(-2);
        return s;
    }

    public int getInt() {
        int i = this.byteBuffer.getInt();
        this.addSize(-4);
        return i;
    }

    public long getLong() {
        long s = this.byteBuffer.getLong();
        this.addSize(-8);
        return s;
    }

    public char getChar() {
        char c = this.byteBuffer.getChar();
        this.addSize(-2);
        return c;
    }

    public float getFloat() {
        float f = this.byteBuffer.getFloat();
        this.addSize(-4);
        return f;
    }

    public double getDouble() {
        double b = this.byteBuffer.getDouble();
        this.addSize(-8);
        return b;
    }

    public String getString(int len) {
        byte[] bytes = new byte[len];
        for(int i = 0; i < len; i ++) {
            bytes[i] = this.byteBuffer.get();
            this.addSize(-1);
        }

        return new String(bytes);
    }

    @Override
    public String toString() {
        return byteBuffer.toString();
    }
}
