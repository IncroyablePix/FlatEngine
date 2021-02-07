package be.helmo.main.screen;

import be.helmo.main.screen.ResolutionChangedListener;

import javax.swing.*;
import java.awt.*;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class Screen {

    public static final int WIN_WIDTH = 1920,
                            WIN_HEIGHT = 1080;

    private final JFrame window;

    private boolean fullscreen;

    private int xRes = 1920,
                yRes = 1080;

    private final int screenWidth;
    private final int screenHeight;

    private int xSize, ySize;
    private float xScale, yScale;
    private float xFactor, yFactor;

    private int xPicture, yPicture;
    private int xMargin, yMargin;

    private float coef;

    private List<ResolutionChangedListener> listeners;

    public Screen(JFrame window) {
        this.window = window;

        xSize = WIN_WIDTH;
        ySize = WIN_HEIGHT - 23;

        xScale = (float) xSize / getXResolution();
        yScale = (float) ySize / getYResolution();

        xFactor = (float) getXResolution() / WIN_WIDTH;
        yFactor = (float) getYResolution() / WIN_HEIGHT;

        coef = Math.min(xScale, yScale);

        this.screenWidth = window.getToolkit().getScreenSize().width;
        this.screenHeight = window.getToolkit().getScreenSize().height;

        this.listeners = new ArrayList<>();
    }

    public void addResolutionChangedListener(ResolutionChangedListener listener) {
        if(listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeResolutionChangedListener(ResolutionChangedListener listener) {
        if(listener != null && listeners.contains(listener))
            listeners.remove(listener);
    }

    public int getMarginX() { return xMargin; }

    public int getMarginY() { return yMargin; }

    public int getPictureX() { return xPicture; }

    public int getPictureY() { return yPicture; }

    public int getXResolution() { return xRes; }

    public int getYResolution() {
        return yRes;
    }

    public float getXFactor() {
        return xFactor;
    }

    public float getYFactor() {
        return yFactor;
    }

    public float getScaleX() { return this.xScale; }

    public float getScaleY() { return this.yScale; }

    public int getScreenWidth() { return screenWidth; }

    public int getScreenHeight() { return screenHeight; }

    public int getSizeX() {
        return xSize;
    }

    public int getSizeY() {
        return ySize;
    }

    public float getCoef() {
        return coef;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void onWindowResized(int x, int y) {
        //System.out.println("x = " + x + " y = " + y);
        //System.out.println("xRes = " + getXResolution() + " yRes = " + getYResolution());
        xSize = x;
        ySize = y;

        xScale = (float) getSizeX() / getXResolution();
        yScale = (float) getSizeY() / getYResolution();
        //System.out.println("xScale = " + xScale + " yScale = " + yScale);

        coef = Math.min(xScale, yScale);
        setGameFrameSize();
    }

    public void setGameFrameSize() {
        xPicture = (int) (getXResolution() * coef);
        yPicture = (int) (getYResolution() * coef);

        if (yScale > xScale)
            yMargin = (getSizeY() - getGameFrameHeight()) >> 1;
        else
            xMargin = (getSizeX() - getGameFrameWidth()) >> 1;
    }

    public void setResolution(final int x, final int y) {
        if (xRes != x && yRes != y) {
            xRes = x;
            yRes = y;

            xFactor = (float) getXResolution() / WIN_WIDTH;
            yFactor = (float) getYResolution() / WIN_HEIGHT;

            window.setSize(new Dimension(x, y));
            window.setLocation(0, 0);

            if (fullscreen)
                window.setBounds(0, 0, getScreenWidth(), getScreenHeight());
            else
                window.setBounds(0, 0, getXResolution(), getYResolution());

            setGameFrameSize();

            for(ResolutionChangedListener listener : listeners)
                listener.onResolutionChanged(fullscreen, x, y);
        }
    }

    public void setFullScreen(boolean fullscreen) {
        if (this.fullscreen != fullscreen) {
            this.fullscreen = fullscreen;

            if (fullscreen) {
                GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
                //System.out.println("screenwidth = " + x + " screenheight = " + y);
                window.dispose();

                window.setBounds(0, 0, getScreenWidth(), getScreenHeight());
                window.setUndecorated(true);
                device.setFullScreenWindow(window);
            }
            else {
                window.setVisible(true);
                window.setBounds(0, 0, getXResolution(), getYResolution());
                window.dispose();
                window.setUndecorated(false);
                window.setVisible(true);
            }
        }
    }

    public int getGameFrameWidth() {
        return fullscreen ? this.getScreenWidth() : this.xPicture;
    }

    public int getGameFrameHeight() {
        return fullscreen ? this.getScreenHeight() : this.yPicture;
    }

    public int getGameFrameMarginX() { return fullscreen ? 0 : this.xMargin; }

    public int getGameFrameMarginY() {
        return fullscreen ? 0 : this.yPicture;
    }

    public int getOnScreenX() { return window.getLocationOnScreen().x; }

    public int getOnScreenY() { return window.getLocationOnScreen().y; }
}
