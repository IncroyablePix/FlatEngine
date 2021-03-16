package com.c4nn4.pix_engine.manager.network.message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetQueue<T> {
    protected Queue<T> deque;

    public <T> NetQueue() {
        deque = new ConcurrentLinkedQueue<>();
    }

    public T peek() {
        return deque.peek();
    }

    public T poll() {
        return deque.poll();
    }

    public void add(T item) {
        deque.add(item);
    }

    public boolean empty() {
        return deque.isEmpty();
    }

    public int count() {
        return deque.size();
    }
}


