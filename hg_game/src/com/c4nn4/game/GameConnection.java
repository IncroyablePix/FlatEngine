package com.c4nn4.game;

import com.c4nn4.network.ConnectionListener;
import com.c4nn4.network.HigherGroundsClient;
import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.graphics.hud.HUD;
import com.c4nn4.pix_engine.graphics.hud.Observer;
import com.c4nn4.pix_engine.graphics.hud.elements.textfield.TextField;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.texts.BlinkingText;
import com.c4nn4.pix_engine.graphics.texts.FadingText;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.fonts.Fonts;
import com.c4nn4.pix_engine.menu.MenuState;

import java.awt.*;

public class GameConnection extends GameState {
    private final static int DEFAULT_PORT = 7777;

    private final String ip;
    private final int port;
    private HigherGroundsClient client;

    private HUD hud;

    private final Text connectionText;
    private Text resultText;

    private final Observer observer;

    public GameConnection(GameStateManager gsm, String ip, int port) {
        super(gsm);
        this.ip = ip;
        this.port = port;
        this.client = new HigherGroundsClient(Debug::log, new GameConnectionListener());
        this.hud = new HUD();
        this.observer = new Observer();
        this.connectionText = new BlinkingText(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 6, -1, Speed.SLOW,
                "Connecting to " + ip + ":" + port, Color.WHITE, Fonts.RIGHTEOUS_B);
        this.connectionText.setAlignement(Alignement.CENTER);

        this.observer.add(connectionText);
    }

    public GameConnection(GameStateManager gsm, String ip) {
        this(gsm,  ip, DEFAULT_PORT);
    }

    @Override
    public void init() {
        initialized = true;

        this.client.connect(this.ip, this.port);
    }

    @Override
    public void update() {
        client.update();
        observer.update();
        hud.update();
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.drawRectangle(0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, Color.BLACK);
        observer.draw(renderer);
        hud.draw(renderer);
    }

    @Override
    public void setState(MenuState menu) {

    }

    @Override
    public void terminate() {
    }

    private class GameConnectionListener implements ConnectionListener {

        @Override
        public void onConnected() {
            observer.add(new FadingText(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT /8, -1, Speed.SLOW,
                    "Vous êtes connecté !", Color.WHITE, Fonts.CHAMP_LIMO_S).setAlignement(Alignement.CENTER));

            observer.remove(connectionText);

            hud.add(new TextField(20, 715, Alignement.LEFT, Color.RED, true, message -> client.onText(message)));
        }

        @Override
        public void onConnectionFailed() {
            observer.add(new FadingText(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 8, -1, Speed.SLOW,
                    "La connection n'a pas pu aboutir", Color.RED, Fonts.CHAMP_LIMO_S).setAlignement(Alignement.CENTER));
            observer.remove(connectionText);
        }

        @Override
        public void onDisconnect() {

        }
    }
}
