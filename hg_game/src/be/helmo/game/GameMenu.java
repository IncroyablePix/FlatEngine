package be.helmo.game;

//import java.awt.Image;
//import java.awt.image.BufferedImage;

import be.helmo.enums.AbstractTiles;
import be.helmo.enums.GameMenus;
import be.helmo.graphics.Element;
import be.helmo.graphics.hud.Observer;
import be.helmo.graphics.overimages.FadingImg;
import be.helmo.graphics.overimages.Img;
import be.helmo.graphics.perlin.CloudyPerlin;
import be.helmo.graphics.perlin.PerlinNoise;
import be.helmo.graphics.Renderer;
import be.helmo.graphics.texts.FadingText;
import be.helmo.graphics.texts.Text;
import be.helmo.graphics.texts.TypeText;
import be.helmo.graphics.TileType;
import be.helmo.main.GameThread;
import be.helmo.main.screen.Screen;
import be.helmo.main.Main;
import be.helmo.manager.*;
import be.helmo.manager.audio.AudioManager;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.image.Content;
import be.helmo.manager.image.PixManager;
import be.helmo.menu.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static be.helmo.manager.image.PixManager.TILE_SIZE;

//import be.helmo.Graphics.Perlin.PerlinNoise;
//import be.helmo.Graphics.Perlin.SpacePerlin;

public class GameMenu extends GameState {

    private GameMenus currentMenu, previousMenu;
    private final MenuControlsHandler controlsHandler;

    private final AudioManager am;

    private final Observer observer;
    private final int ticks;

    private MenuState menuState;

    private final PerlinNoise background;
    private BufferedImage perlin;
    //private Img back;
    private final Img cloud;
    private final Img[] ground;

    public GameMenu(final GameStateManager gsm) {
        super(gsm);

        debug("Loading GameMenu...");

        am = AudioManager.get();
        am.load("/be/helmo/resources/Sound/Music/ambiantlow.mp3", "menu", true);
        am.setVolume("menu", -10);

        background = new PerlinNoise(320, 240, 20, new CloudyPerlin());

        //back = new Img(0, GameWindow.WIN_HEIGHT, GameWindow.WIN_WIDTH, GameWindow.WIN_HEIGHT, -1, PixManager.get().sky(), 1.f);
        cloud = new Img(-1094, 591, 200, 591, -1, Content.load("/be/helmo/resources/Graphics/Others/CLOUD_1.png"), 0.3f);

        //---
        ground = new Img[(Screen.WIN_WIDTH / TILE_SIZE)];
        PixManager pm = PixManager.get();

        for (int i = 0; i < ground.length; i++) {
            TileType tt;
            if (i == 0)
                tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_TOP_LEFT));
            else if (i == ground.length - 1)
                tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_TOP_RIGHT));
            else
                tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_TOP_MIDDLE));

            ground[i] = new Img(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE, -1, tt.getImage(), 1.f);
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
        am.stop("intro");
        am.loop("menu");

        //bg = Content.MENUBG[0][0];
    }

    public void update() {
        //Debug.log("updating GameMenu " + GameThread.ticksFrom(ticks) + " current ticks : " + GameThread.ticks());

        if (GameThread.ticksFrom(ticks) == 60) {
            FadingImg logo = new FadingImg(710, 960, 500, 200, -1, Element.SPEED_SLOW, Content.load("/be/helmo/resources/Graphics/Others/TITLE.png"));
            Text version = new FadingText(1140, 780, -1, Element.SPEED_SLOW, Main.VERSION, Color.PINK, FontManager.COURIER_T);
            Text bugs = new TypeText(1200, 750, 500, Element.SPEED_FAST, Main.BUGS, Color.BLACK, FontManager.COURIER_T);

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

        if (menuState != null) {
            menuState.draw(renderer);
        }

        if (observer != null) {
            observer.draw(renderer);
        }
    }

    private void selectOption() {
        menuState.selectOption();
    }

    private boolean subMenu(GameMenus parent, GameMenus child) {
        return (parent == GameMenus.O_VIDEO_MENU && child == GameMenus.OPTIONS_MENU) ||
                (parent == GameMenus.OPTIONS_MENU && child == GameMenus.O_VIDEO_MENU);
    }

    private boolean isOptionsMenu(final GameMenus menu) {
        return menu == GameMenus.O_VIDEO_MENU;
    }

    public void setState(GameMenus menu) {
        if (currentMenu != GameMenus.NO_MENU) {
            //cloud.move((int) (1.5 * GameWindow.WIN_WIDTH), 591, 300);
            //background.setSmoothAcceleration(0.001, 0.01);
        }

        if (!subMenu(currentMenu, menu)) {
            previousMenu = currentMenu;
        }
        else if (menu == GameMenus.OPTIONS_MENU && isOptionsMenu(currentMenu)) {
            gsm.saveSettings();
        }
        unloadMenu(previousMenu);

        currentMenu = menu;

        switch (menu) {
            case NO_MENU: {
                menuState = null;//new MenuState(gsm);
                break;
            }
            case MAIN_MENU: {
                menuState = new MainMenu(gsm);
                break;
            }
            case OPTIONS_MENU: {
                menuState = new MenuOptions(gsm, previousMenu);
                break;
            }
            case O_VIDEO_MENU: {
                menuState = new MenuVideo(gsm, GameMenus.OPTIONS_MENU);
                break;
            }
            case QUIT_MENU: {
                menuState = new MenuQuit(gsm, previousMenu);
                break;
            }
            case GAME_OVER_MENU: {
                menuState = new MenuGameOver(gsm);
                break;
            }
			/*case FAKE_MENU: {
				menuStates[menu] = new FakeMenu(gsm);
				break;
			}
			case GAME_OVER: {
				menuStates[menu] = new GameOver(this);
				break;
			}*/
        }

        menuState.init();
    }

    @Override
    public void terminate() {
        Controls.get().removeListener(controlsHandler);
    }

    public void unloadMenu(GameMenus menu) {
        menuState = null;
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
