package client;

import com.c4nn4.pix_engine.manager.network.connection.Client;
import com.c4nn4.pix_engine.manager.network.message.Message;
import com.c4nn4.pix_engine.manager.network.message.Messageable;
import com.c4nn4.pix_engine.manager.network.message.OwnedMessage;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class ClientTest {
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

    private static class TestClient extends Client<TestMessages> {
        public void ping() {
            Message<TestMessages> message = new Message<>(TestMessages.ServerPing);
            message.add(ZonedDateTime.now().toInstant().toEpochMilli());
            send(message);
        }

        public void messageAll(String text) {
            Message<TestMessages> message = new Message<>(TestMessages.MessageAll);
            message.add(text);
            send(message);
        }
    }

    public static void main(String[] args) {
        final boolean[] running = {true};
        TestClient c = new TestClient();
        if(!c.connect("127.0.0.1", 7777)) {
            System.err.println("Connection impossible !");
            return;
        }


        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(running[0]) {
                String line = scanner.nextLine();

                if(c.isConnected()) {
                    if (line.equals("quit")) {
                        c.disconnect();
                        running[0] = false;
                    }
                    else if (line.equals("ping")) {
                        c.ping();
                    }
                    else {
                        c.messageAll(line);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while(running[0]) {
                if (c.isConnected()) {
                    if(!c.incoming().empty()) {
                        OwnedMessage<TestMessages> message = c.incoming().poll();
                        TestMessages messageType = TestMessages.getTypeFromId(message.getMessage().header()[0]);

                        if(messageType != null) {
                            switch (messageType) {
                                case ServerAccept:
                                    System.out.println("Server accepted connection !");
                                    break;
                                case ServerDeny:
                                    String errorMsg = message.getMessage().getString(message.getMessage().header()[1]);
                                    System.err.println(errorMsg);
                                    c.disconnect();
                                    break;
                                case ServerPing:
                                    long now = ZonedDateTime.now().toInstant().toEpochMilli();
                                    long then = message.getMessage().getLong();

                                    System.out.println("Ping: " + (now - then));
                                    break;
                                case ServerMessage:
                                    String msg = message.getMessage().getString(message.getMessage().header()[1]);
                                    System.out.println(msg);
                                    break;
                            }
                        }
                    }
                }
                else {
                    running[0] = false;
                }
            }
        }).start();
    }
}
