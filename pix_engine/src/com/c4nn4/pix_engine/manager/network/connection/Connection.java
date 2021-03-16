package com.c4nn4.pix_engine.manager.network.connection;

import com.c4nn4.pix_engine.manager.network.message.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

public class Connection<T extends Messageable> implements Runnable {
    private int id;

    private boolean running;

    protected NetQueue<Message<T>> toSend;
    protected NetQueue<OwnedMessage<T>> toRead;

    protected Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;

    protected Message<T> tempMessage;

    protected Owner owner;

    public Connection(Owner owner,
                      NetQueue<OwnedMessage<T>> toRead,
                      NetQueue<Message<T>> toSend) {
        this(owner, new Socket(), toRead, toSend);
    }

    public Connection(Owner owner, Socket socket,
                      NetQueue<OwnedMessage<T>> toRead,
                      NetQueue<Message<T>> toSend) {
        this.owner = owner;
        this.toRead = toRead;
        this.toSend = toSend;

        this.socket = socket;
        this.writer = null;
        this.reader = null;

        this.id = 0;
    }

    public int getId() {
        return this.id;
    }

    public void connectToClient(int id) throws IOException {
        if(owner == Owner.Server) {
            if(socket.isConnected()) {
                this.id = id;
                this.writer = new DataOutputStream(this.socket.getOutputStream());
                this.reader = new DataInputStream(this.socket.getInputStream());
                //readHeader();
            }
        }
    }

    public void connectToServer(SocketAddress endpoint) throws IOException {
        if(owner == Owner.Client) {
            this.socket.connect(endpoint);
            this.writer = new DataOutputStream(this.socket.getOutputStream());
            this.reader = new DataInputStream(this.socket.getInputStream());
        }
    }

    public void send(final Message<T> message) {
        toSend.add(message);
        writeHeader();
    }

    private void writeHeader() {
        try {
            Message<T> message = toSend.poll();

            if(message != null) {
                int[] header = message.header();
                this.writer.writeInt(header[0]);
                this.writer.writeInt(header[1]);

                if (message.getSize() > 0) {
                    writeBody(message);
                }
                else {
                    if (!toSend.empty())
                        writeHeader();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            closeSocket(socket);
        }
    }

    private void closeSocket(Socket socket) {
        try {
            socket.close();
        }
        catch (IOException ignored) {
            // Tant pis
        }
    }

    private void writeBody(Message<T> message) throws IOException {
        message.resetCursor();
        while(message.getSize() > 0)
            this.writer.writeByte(message.getByte());

        this.writer.flush();

        if(!toSend.empty())
            writeHeader();
    }

    private void readHeader() {
        try {
            int type = reader.readInt();
            int len = reader.readInt();
            tempMessage = new Message<T>(type);

            if(len > 0) {
                readBody(len);
            }
            else {
                addToIncommingQueue();
            }
        }
        catch (IOException e) {
            closeSocket(socket);
        }
    }

    private void readBody(int len) throws IOException {
        for(int i = 0; i < len; i ++)
            tempMessage.add(reader.readByte());

        tempMessage.resetCursor();
        addToIncommingQueue();
    }

    private void addToIncommingQueue() {
        toRead.add(new OwnedMessage<>(this.owner == Owner.Server ? this : null, tempMessage));
    }

    public boolean isConnected() {
        return this.socket.isConnected() && !this.socket.isClosed();
    }

    public void disconnect() {
        closeSocket(this.socket);
        this.running = false;
    }

    @Override
    public void run() {
        this.running = true;

        while(this.running) {
            readHeader();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection<?> that = (Connection<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
