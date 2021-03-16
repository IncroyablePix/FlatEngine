package server;

import com.c4nn4.pix_engine.manager.network.connection.Connection;
import com.c4nn4.pix_engine.manager.network.connection.Server;
import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.Messageable;

import java.io.IOException;

public class ServerTest {
    private enum TestMessages implements Messageable {
        ServerAccept,
        ServerDeny,
        ServerPing,
        MessageAll,
        ServerMessage;

        public int getType() {
            return this.ordinal();
        }

        public static TestMessages getTypeFromId(int id) {
            if(id > TestMessages.values().length)
                return null;

            for(TestMessages message : TestMessages.values()) {
                if(message.ordinal() == id)
                    return message;
            }

            return null;
        }
    }

    private static class TestServer extends Server<TestMessages> {

        public TestServer(int port) throws IOException {
            super(port);
        }

        @Override
        protected boolean onClientConnect(Connection<TestMessages> client) {
            Message<TestMessages> welcomeMessage = new Message<>(TestMessages.ServerAccept);
            client.send(welcomeMessage);

            System.out.println("Welcome client " + client.getId() + "!");

            return true;

            /*Message<TestMessages> noNo = new Message<>(TestMessages.ServerDeny);
            noNo.add("Casse-toi PD !");
            client.send(noNo);
            System.out.println("Casse-toi client " + client.getId() + "!");

            return false;*/
        }

        @Override
        protected void onClientDisconnect(Connection<TestMessages> client) {
            System.out.println("Goodbye, client + " + client.getId() + "!");
        }

        @Override
        protected void onClientTriesToJoinFull(Connection<TestMessages> connection) {
            Message<TestMessages> noNo = new Message<>(TestMessages.ServerDeny);
            noNo.add("Casse-toi PD !");
            connection.send(noNo);
        }

        @Override
        protected void onMessage(Connection<TestMessages> client, Message<TestMessages> message) {
            int[] header = message.header();
            TestMessages msgType = TestMessages.getTypeFromId(header[0]);
            if(msgType != null) {
                switch (msgType) {
                    case ServerPing: {
                        client.send(message);
                        break;
                    }
                    case MessageAll: {
                        String say = message.getString(header[1]);
                        System.out.println("Client " + client.getId() + ": " + say);

                        Message<TestMessages> bounce = new Message<>(TestMessages.ServerMessage);
                        bounce.add(client.getId() + ": ");
                        bounce.add(say);
                        sendClientMessageToAll(bounce, client);
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            TestServer server = new TestServer(7777);
            new Thread(server).start();

            while(true) {
                server.update(1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
