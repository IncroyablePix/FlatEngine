package com.c4nn4;

import com.c4nn4.pix_engine.manager.network.connection.Connection;
import com.c4nn4.pix_engine.manager.network.connection.Server;
import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.Messageable;

import java.io.IOException;

public class HigherGroundsServer {
    private enum HGMessages implements Messageable {
        ServerAccept,
        ServerDeny,
        ServerPing,
        MessageAll,
        ServerMessage;

        public int getType() {
            return this.ordinal();
        }

        public static HGMessages getTypeFromId(int id) {
            if(id > HGMessages.values().length)
                return null;

            for(HGMessages message : HGMessages.values()) {
                if(message.ordinal() == id)
                    return message;
            }

            return null;
        }
    }

    private static class TestServer extends Server<HGMessages> {

        public TestServer(int port) throws IOException {
            super(port);
        }

        @Override
        protected boolean onClientConnect(Connection<HGMessages> client) {
            Message<HGMessages> welcomeMessage = new Message<>(HGMessages.ServerAccept);
            welcomeMessage.add("Bienvenue sur le serveur de test !");
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
        protected void onClientDisconnect(Connection<HGMessages> client) {
            System.out.println("Goodbye, client " + client.getId() + "!");
        }

        @Override
        protected void onClientTriesToJoinFull(Connection<HGMessages> connection) {
            Message<HGMessages> noNo = new Message<>(HGMessages.ServerDeny);
            noNo.add("Casse-toi PD !");
            connection.send(noNo);
        }

        @Override
        protected void onMessage(Connection<HGMessages> client, Message<HGMessages> message) {
            int[] header = message.header();
            HGMessages msgType = HGMessages.getTypeFromId(header[0]);
            if(msgType != null) {
                switch (msgType) {
                    case ServerPing: {
                        client.send(message);
                        break;
                    }
                    case MessageAll: {
                        String say = message.getString(header[1]);
                        System.out.println("Client " + client.getId() + ": " + say);

                        Message<HGMessages> bounce = new Message<>(HGMessages.ServerMessage);
                        bounce.add(client.getId() + ": ");
                        bounce.add(say);
                        sendClientMessageToAll(bounce, null);//client);
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
