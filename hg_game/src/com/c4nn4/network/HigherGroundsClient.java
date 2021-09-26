package com.c4nn4.network;

import com.c4nn4.pix_engine.manager.network.connection.Client;
import com.c4nn4.pix_engine.manager.network.connection.Disconnection;
import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.OwnedMessage;

import java.time.ZonedDateTime;

public class HigherGroundsClient extends Client<HGMessages> {
    private boolean running;
    private NetTextListener netTextListener;
    private ConnectionListener connectionListener;

    public HigherGroundsClient(NetTextListener textListener, ConnectionListener connectionListener) {
        this.running = false;
        this.netTextListener = textListener;
        this.connectionListener = connectionListener;
    }

    public void update() {
        if (isConnected()) {
            if(!incoming().empty()) {
                OwnedMessage<HGMessages> message = incoming().poll();
                HGMessages messageType = HGMessages.getTypeFromId(message.getMessageId());

                if(messageType != null) {
                    switch (messageType) {
                        case ServerAccept:
                            netTextListener.onTextArrived(message.getMessage().getString(message.getSize()));
                            break;
                        case ServerDeny:
                            netTextListener.onTextArrived(message.getMessage().getString(message.getSize()));
                            disconnect();
                            break;
                        case ServerPing:
                            long now = ZonedDateTime.now().toInstant().toEpochMilli();
                            long then = message.getMessage().getLong();

                            System.out.println("Ping: " + (now - then));
                            netTextListener.onTextArrived("Ping: " + (now - then));
                            break;
                        case ServerMessage:
                            netTextListener.onTextArrived(message.getMessage().getString(message.getSize()));
                            break;
                    }
                }
            }
        }
    }

    public void ping() {
        Message<HGMessages> message = new Message<>(HGMessages.ServerPing);
        message.add(ZonedDateTime.now().toInstant().toEpochMilli());
        send(message);
    }

    public void messageAll(String text) {
        Message<HGMessages> message = new Message<>(HGMessages.MessageAll);
        message.add(text);
        send(message);
    }

    public void quit() {
        disconnect();
    }

    private void sendCommand(String[] cmd) {
        if(cmd != null) {
            switch (cmd[0]) {
                case "ping":
                    ping();
                    break;
                case "quit":
                    quit();
                    break;
            }
        }
    }

    public void onText(String message) {
        if(message != null && !message.isEmpty()) {
            if(message.charAt(0) == '/') {
                sendCommand(message.substring(1).split(" "));
            }
            else {
                messageAll(message);
            }
        }
    }

    @Override
    protected void onDisconnect(Disconnection type) {
        this.connectionListener.onDisconnect();
    }

    @Override
    protected void onConnected() {
        this.connectionListener.onConnected();
    }

    @Override
    protected void onConnectionFailed() {
        this.connectionListener.onConnectionFailed();
    }
}
