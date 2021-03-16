package com.c4nn4.pix_engine.manager.network.connection;

import com.c4nn4.pix_engine.manager.debug.Debug;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionManager implements Runnable {
    private Socket socket;
    private boolean running;

    private BufferedReader fromServer;
    private PrintWriter toServer;

    public ConnectionManager(Socket socket) throws ConnectionFailedException {
        if(socket == null)
            throw new IllegalArgumentException("Input socket cannot be null");

        try {
            this.socket = socket;
            this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            this.running = true;
        }
        catch (IOException e) {
            throw new ConnectionFailedException(e);
        }
    }

    public void terminate() {
        try {
            running = false;
            socket.close();
        }
        catch (IOException e) {
            Debug.log("The connection could not be properly closed!");
        }
    }

    @Override
    public void run() {
        while(running) {
             try {
                 String line = readServerMessage(fromServer);
                 Debug.log("Received message from " + getServerName() + " : " + line);
             }
             catch(IOException e) {

             }
        }
    }

    private String getServerName() {
        return socket.getInetAddress() + ":" + socket.getPort();
    }

    public void sendServerMessage(String msg) {
        toServer.write(msg);
        toServer.flush();
    }

    private String readServerMessage(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        if(line != null && line.length() > 2 && line.startsWith("\uFEFF"))
            return line.substring("\uFEFF".length());

        return line;
    }
}
