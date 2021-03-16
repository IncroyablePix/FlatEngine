package com.c4nn4.game;

import com.c4nn4.menu.GameMenus;
import com.c4nn4.menu.MenuVideo;
import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.graphics.hud.Observer;
import com.c4nn4.pix_engine.graphics.overimages.FadingImg;
import com.c4nn4.pix_engine.graphics.overimages.Img;
import com.c4nn4.pix_engine.graphics.perlin.CloudyPerlin;
import com.c4nn4.pix_engine.graphics.perlin.PerlinNoise;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.FadingText;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.graphics.texts.TypeText;
import com.c4nn4.pix_engine.main.GameThread;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.main.Main;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.manager.audio.AudioManager;
import com.c4nn4.pix_engine.manager.controls.ControlListener;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.fonts.Fonts;
import com.c4nn4.pix_engine.manager.image.Content;
import com.c4nn4.pix_engine.manager.image.PixManager;
import com.c4nn4.pix_engine.menu.MenuState;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.c4nn4.pix_engine.manager.image.PixManager.TILE_SIZE;

//import be.helmo.Graphics.Perlin.PerlinNoise;
//import be.helmo.Graphics.Perlin.SpacePerlin;

public class GameMenu extends GameState {

    private GameMenus currentMenu, previousMenu;
    private final MenuControlsHandler controlsHandler;

    private final Observer observer;
    private final int ticks;

    private MenuState menuState, previousMenuState;

    private final PerlinNoise background;
    private BufferedImage perlin;
    //private Img back;
    private final Img cloud;
    private final Img[] ground;

    public GameMenu(final GameStateManager gsm) {
        super(gsm);

        debug("Loading GameMenu...");

        PixManager.get().loadBigImage(PixManager.TITLE, "TITLE");

        background = new PerlinNoise(320, 240, 20, new CloudyPerlin());

        //back = new Img(0, GameWindow.WIN_HEIGHT, GameWindow.WIN_WIDTH, GameWindow.WIN_HEIGHT, -1, PixManager.get().sky(), 1.f);
        cloud = new Img(-1094, 591, 200, 591, -1, Content.load("/com/c4nn4/resources/Graphics/Others/CLOUD_1.png"), 0.3f);

        //---
        ground = new Img[(Screen.WIN_WIDTH / TILE_SIZE)];
        PixManager pm = PixManager.get();
        pm.unloadSpriteSheet("TILES");
        pm.loadSpriteSheet(PixManager.TILES, "TILES", TILE_SIZE, TILE_SIZE);

        for (int i = 0; i < ground.length; i++) {
            BufferedImage image;
            if (i == 0)
                image = pm.getSprite("TILES", 0);
            else if (i == ground.length - 1)
                image = pm.getSprite("TILES", 3);
            else
                image = pm.getSprite("TILES", 2);

            ground[i] = new Img(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE, -1, image, 1.f);
        }
        //cloud.attachDebug(debug);

        controlsHandler = new MenuControlsHandler();
        Controls.get().addListener(controlsHandler);

        observer = new Observer();
        observer.add(cloud);

        ticks = GameThread.ticks();
        Debug.log("initial ticks " + ticks);

        debug("Loaded.");
    }

    public void init() {
        AudioManager am = AudioManager.get();
        am.stop("intro");

        am = AudioManager.get();
        am.load("/com/c4nn4/resources/Sound/Music/ambiantlow.mp3", "menu");
        am.setVolume("menu", -10);
        am.loop("menu");
        initialized = true;
        //bg = Content.MENUBG[0][0];
    }

    public void update() {
        //Debug.log("updating GameMenu " + GameThread.ticksFrom(ticks) + " current ticks : " + GameThread.ticks());

        if (GameThread.ticksFrom(ticks) == 60) {
            FadingImg logo = new FadingImg(710, 960, 500, 200, -1, Speed.SLOW, Content.load(PixManager.TITLE));
            Text version = new FadingText(1140, 780, -1, Speed.SLOW, Main.VERSION, Color.PINK, Fonts.COURIER_T);
            Text bugs = new TypeText(1200, 750, 500, Speed.FAST, Main.BUGS, Color.BLACK, Fonts.COURIER_T);

            observer.add(logo);
            observer.add(version);
            observer.add(bugs);
        }

        if (!cloud.isMoving()) {
            cloud.setPos(-1094, 591);
            cloud.move((int) (1.5 * Screen.WIN_WIDTH), 591, 1);
        }

        if (menuState != null) {
            menuState.update();
        }

        observer.update();
    }

    public void draw(final Renderer renderer) {

        perlin = background.getNoiseImage();
        renderer.drawImage(perlin, 0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, 1.f);

        for (int i = 0; i < ground.length; i++)
            ground[i].draw(renderer);

        cloud.draw(renderer);

        if(menuState != null)
            menuState.draw(renderer);

        observer.draw(renderer);
    }

    private void selectOption() {
        menuState.selectOption();
    }

    public void setState(MenuState menu) {
        previousMenuState = menuState;

        if(previousMenuState instanceof MenuVideo)
            gsm.saveSettings();

        unloadMenu();

        menuState = menu;

        menuState.init();
    }

    @Override
    public void terminate() {
        Controls.get().removeListener(controlsHandler);
    }

    public void unloadMenu() {
        if(menuState != null) {
            menuState.terminate();
            menuState = null;
        }
    }

    private class MenuControlsHandler implements ControlListener {
        private boolean paused;

        @Override
        public void onKeyInputChanged(int down, int pressed, int released) {
            if ((pressed & Controls.DOWN) == Controls.DOWN) {
                menuState.setMenuChoice(false);
            }
            if ((pressed & Controls.UP) == Controls.UP) {
                menuState.setMenuChoice(true);
            }
            if ((pressed & Controls.LEFT) == Controls.LEFT) {
                menuState.switchOption(false);
            }
            if ((pressed & Controls.RIGHT) == Controls.RIGHT) {
                menuState.switchOption(true);
            }
            if ((pressed & Controls.CHECK) == Controls.CHECK) {
                selectOption();
            }
        }

        @Override
        public boolean isPaused() {
            return paused;
        }

        @Override
        public void pause(boolean paused) {
            this.paused = paused;
        }
    }
}
