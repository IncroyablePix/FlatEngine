package com.c4nn4.pix_engine.main.screen;

import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.DisplayThread;
import com.c4nn4.pix_engine.graphics.render.GRenderer;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.main.GameThread;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.controls.keyboard.Keys;
import com.c4nn4.pix_engine.manager.controls.mouse.Mouse;
import com.c4nn4.pix_engine.manager.fonts.FontManager;
import com.c4nn4.pix_engine.manager.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("serial")
public class GameWindow extends JPanel implements ResolutionChangedListener {
    private Thread threadGraphics;
    private Thread threadUpdate;

    private final JFrame window;

    private BufferedImage img;
    private Graphics2D g;

    private final GameStateManager gsm;
    private final DisplayThread display;

    private final Screen screen;

    private final FontManager fonts;
    private Renderer renderer;

    private final Keys keys;
    private final Mouse mouse;

    private final String title;

    public GameWindow(JFrame window, Screen screen, String gameTitle, Constructor<? extends GameState> constructor) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        this.window = window;
        this.fonts = new FontManager();
        this.screen = screen;

        screen.addResolutionChangedListener(fonts);

        setPreferredSize(new Dimension(Screen.WIN_WIDTH, Screen.WIN_HEIGHT));//Fullscreen fonctionne

        display = new DisplayThread(this);

        keys = new Keys();
        mouse = new Mouse(screen);

        Controls.get().setKeyboard(keys);
        Controls.get().setMouse(mouse);

        //init();
        imageInit();

        gsm = new GameStateManager(this, (Constructor<GameState>) constructor);

        this.title = gameTitle;

        if (!isFullscreen())
            window.setBounds(0, 0, getXResolution(), getYResolution());
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (threadUpdate == null) {
            addKeyListener(keys);
            addMouseListener(mouse);
            addMouseWheelListener(mouse);

            threadUpdate = new Thread(new GameThread(this));
            threadUpdate.start();
            threadUpdate.setName("Update");
        }

        if (threadGraphics == null) {
            threadGraphics = new Thread(display);
            threadGraphics.start();
            threadGraphics.setName("Graphics Displayer");
        }
		/*if(thread == null) {
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}*/
    }

    public void init() {
        fonts.scaleFonts(Math.min(getXFactor(), getYFactor()));

        this.renderer = new GRenderer(this, g, fonts);

        setFocusable(true);
        requestFocus();
    }

    private void imageInit() {
        img = new BufferedImage(getXResolution(), getYResolution(), 1);

        g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (renderer != null && renderer instanceof GRenderer) {
            ((GRenderer) renderer).setGraphics(g);
        }
    }

    public int getXResolution() { return screen.getXResolution(); }

    public int getYResolution() {
        return screen.getYResolution();
    }

    public float getXFactor() {
        return screen.getXFactor();
    }

    public float getYFactor() {
        return screen.getYFactor();
    }

    public int getScreenWidth() { return screen.getScreenWidth(); }

    public int getScreenHeight() { return screen.getScreenHeight(); }

    public void update() {
        gsm.update();
        Controls.get().update();
    }

    public void render() {
        draw();
        drawToScreen();
    }

    public void draw() {
        if (renderer != null)
            gsm.draw(renderer);
    }

    private void drawToScreen() {
        Graphics2D g2 = (Graphics2D) getGraphics();
        float xScale = screen.getScaleX(), yScale = screen.getScaleY();

        if (g2 != null) {
            if (screen.isFullscreen()) {
                g2.drawImage(img, 0, 0, getScreenWidth(), getScreenHeight(), null);
            }
            else {
                int xMargin = screen.getMarginX(), yMargin = screen.getMarginY(),
                        xPicture = screen.getPictureX(), yPicture = screen.getPictureY();
                g2.setColor(Color.WHITE);

                //---Centering
                if (yScale > xScale) {
                    g2.fillRect(0, 0, getSizeX(), yMargin);
                    g2.fillRect(0, getSizeY() - yMargin, getSizeX(), getSizeY());
                }
                else {
                    g2.fillRect(0, 0, xMargin, getSizeY());
                    g2.fillRect(getSizeX() - xMargin, 0, getSizeX(), getSizeY());
                }

                g2.drawImage(img, xMargin, yMargin, xPicture, yPicture, null);
            }
        }
    }

    public void setResolution(final int x, final int y) {
        screen.setResolution(x, y);
    }

    public void setFullScreen(boolean fullscreen) {
        screen.setFullScreen(fullscreen);
    }

    public int getSizeX() {
        return screen.getSizeX();
    }

    public int getSizeY() {
        return screen.getSizeY();
    }

    public float getCoef() {
        return screen.getCoef();
    }

    public boolean isFullscreen() {
        return screen.isFullscreen();
    }

    public void setFPSLimit(final boolean fpsLimit) {
        display.setFPSLimit(fpsLimit);
    }

    public void setFPS(int fps) {
        window.setTitle(this.title + " " + fps + "FPS");
    }

    public boolean getFPSLimit() {
        return display.getFPSLimit();
    }

    @Override
    public void onResolutionChanged(boolean fullscreen, int x, int y) {
        imageInit();
        if (renderer != null)
            renderer.refreshDrawer();
    }
}
