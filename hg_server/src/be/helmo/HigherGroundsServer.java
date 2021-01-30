package be.helmo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HigherGroundsServer {
    public static void main(String[] args) {
        Server server = new Server(7777);
        server.start();

        InetAddress address = null;
        try {
            address = InetAddress.getByName("192.168.1.10");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = 7777;

        try {
            server.send(new byte[] {0, 1, 2}, address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
