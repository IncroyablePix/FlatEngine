package com.c4nn4.pix_engine.manager.network.connection;

import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.Messageable;
import com.c4nn4.pix_engine.manager.network.message.NetQueue;
import com.c4nn4.pix_engine.manager.network.message.OwnedMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server<T extends Messageable> implements Runnable {
    private static final int MAX_CLIENTS = 3;

    private boolean running;

    private NetQueue<OwnedMessage<T>> toRead;
    private Connection<T>[] clients;

    private final ServerSocket socket;
    private int counter;

    public Server(int port) throws IOException {
        this(port, MAX_CLIENTS);
    }

    public Server(int port, int maxClients) throws IOException {
        this.socket = new ServerSocket(port);
        this.clients = new Connection[maxClients];

        this.toRead = new NetQueue<>();
        this.counter = 0;
    }

    public void sendClientMessage(Connection<T> client, Message<T> message) {
        if(client != null && client.isConnected()) {
            client.send(message);
        }
        else {
            onClientDisconnect(client);
            clients[client.getId()] = null;
            client.disconnect();
        }
    }

    public void sendClientMessageToAll(Message<T> message, Connection<T> ignore) {
        Set<Integer> toRemove = new HashSet<>();

        for(Connection<T> client : clients) {
            if(client != null && client.isConnected()) {
                if(client.getId() != ignore.getId()) {
                    message.resetCursor();
                    client.send(message);
                }
            }
            else {
                onClientDisconnect(client);
                toRemove.add(client.getId());
                client.disconnect();
            }
        }

        if(!toRemove.isEmpty()) {
            for(int id : toRemove)
                clients[id] = null;
        }
    }

    public void update(int maxMessages) {
        int count = 0;

        while(count < maxMessages && !toRead.empty()) {
            OwnedMessage<T> message = toRead.poll();
            onMessage(message.getRemote(), message.getMessage());
            count ++;
        }
    }

    @Override
    public void run() {
        this.running = true;

        while(this.running) {
            try {
                Socket client = socket.accept();
                Connection<T> connection = new Connection<>(Owner.Server, client, toRead, new NetQueue<>());
                int id = firstEmptySlot();

                connection.connectToClient(id);
                new Thread(connection).start();

                if(id == -1) {
                    onClientTriesToJoinFull(connection);
                }
                else {
                    if(onClientConnect(connection)) {
                        this.clients[id] = connection;
                    }
                    else {
                        connection.disconnect();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onClientTriesToJoinFull(Connection<T> connection) {
    }

    protected boolean onClientConnect(Connection<T> client) {
        return true;
    }

    protected void onClientDisconnect(Connection<T> client) {

    }

    protected void onMessage(Connection<T> client, Message<T> message) {

    }

    private int firstEmptySlot() {
        for(int i = 0; i < clients.length; i ++) {
            if(clients[i] == null)
                return i;
        }

        return -1;
    }

    public void close() {
        this.running = false;
    }
}
