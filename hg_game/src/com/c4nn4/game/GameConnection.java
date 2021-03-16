package com.c4nn4.game;

import com.c4nn4.network.HGMessages;
import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.hud.Observer;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.BlinkingText;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.graphics.texts.TypeText;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.fonts.Fonts;
import com.c4nn4.pix_engine.manager.network.connection.Client;
import com.c4nn4.pix_engine.manager.network.connection.ConnectionInitializer;
import com.c4nn4.pix_engine.manager.network.connection.ConnectionManager;
import com.c4nn4.pix_engine.menu.MenuState;


import java.awt.*;
import java.net.Socket;

public class GameConnection extends GameState {
    private final static int DEFAULT_PORT = 7777;

    private final String ip;
    private final int port;
    private Client<HGMessages> client;

    private final Text connectionText;
    private Text resultText;

    private final Observer observer;

    public GameConnection(GameStateManager gsm, String ip, int port) {
        super(gsm);
        this.ip = ip;
        this.port = port;
        this.client = new Client<>();
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

        if(this.client.connect(this.ip, this.port)) {

        }
        else {

        }
    }

    @Override
    public void update() {
        observer.update();
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
