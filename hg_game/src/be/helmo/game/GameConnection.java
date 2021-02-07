package be.helmo.game;

import be.helmo.graphics.texts.Alignement;
import be.helmo.graphics.Speed;
import be.helmo.graphics.hud.Observer;
import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.texts.BlinkingText;
import be.helmo.graphics.texts.Text;
import be.helmo.graphics.texts.TypeText;
import be.helmo.main.screen.Screen;
import be.helmo.manager.GameStateManager;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.fonts.Fonts;
import be.helmo.manager.network.connection.ConnectionInitializer;
import be.helmo.manager.network.connection.ConnectionManager;
import be.helmo.menu.MenuState;

import java.awt.*;
import java.net.Socket;

public class GameConnection extends GameState {
    private final static int DEFAULT_PORT = 7777;

    private final String ip;
    private final int port;
    private Socket socket;
    private ConnectionManager connection;
    private Thread connectionThread;

    private final Text connectionText;
    private final ConnectionInitializer connectionInitializer;
    private Text resultText;

    private final Observer observer;

    public GameConnection(GameStateManager gsm, ConnectionInitializer connectionInitializer, String ip, int port) {
        super(gsm);
        this.ip = ip;
        this.port = port;
        this.connectionInitializer = connectionInitializer;
        this.observer = new Observer();
        this.connectionText = new BlinkingText(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 6, -1, Speed.SLOW,
                "Connecting to " + ip + ":" + port, Color.WHITE, Fonts.RIGHTEOUS_B);
        this.connectionText.setAlignement(Alignement.CENTER);

        this.socket = null;

        this.connection = null;

        this.observer.add(connectionText);
    }

    public GameConnection(GameStateManager gsm, ConnectionInitializer connectionInitializer, String ip) {
        this(gsm, connectionInitializer, ip, DEFAULT_PORT);
    }

    @Override
    public void init() {
        initialized = true;

        connectionInitializer.connect(ip, port, (socket) -> {
            if(socket == null) {
                resultText = new TypeText(Screen.WIN_WIDTH / 2, 140, 300, Speed.MEDIUM, "La connection au serveur est impossible !", Color.decode("#990000"), Fonts.RIGHTEOUS_S);
                resultText.setAlignement(Alignement.CENTER);
                observer.add(resultText);
                Debug.log("La connection au serveur a échoué !");
            }
            else {
                this.socket = socket;
            }
        });
    }

    @Override
    public void update() {
        observer.update();

        if(socket != null && connection == null) {
            connection = new ConnectionManager(socket);
            connectionThread = new Thread(connection);
            connectionThread.setName("Server listener");
            connectionThread.start();

            connection.sendServerMessage("coucou serveur");
        }
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.drawRectangle(0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, Color.BLACK);
        observer.draw(renderer);
    }

    @Override
    public void setState(MenuState menu) {

    }

    @Override
    public void terminate() {

    }
}
