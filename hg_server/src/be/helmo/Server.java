package be.helmo;

import java.io.IOException;
import java.net.*;

public class Server implements AutoCloseable {
    private final int port;

    private Thread listenThread;
    private boolean listening = false;
    private DatagramSocket socket;

    private final int MAX_PACKET_SIZE = 1024;
    private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            socket = new DatagramSocket(getPort());
        } catch (SocketException e) {
            throw new ServerInitializationException("The server could not be initialized !", e);
        }

        listenThread = new Thread(() -> listen());

        listening = true;
        listenThread.start();
    }

    private void listen() {
        while(listening) {
            DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
            try {
                socket.receive(packet);
                process(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(DatagramPacket packet) {

    }

    public void send(byte[] data, InetAddress address, int port) throws IOException {
        assert(socket.isConnected());

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }

    public int getPort() {
        return port;
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
