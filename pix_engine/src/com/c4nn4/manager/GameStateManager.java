package com.c4nn4.manager;

import com.c4nn4.game.GameState;
import com.c4nn4.game.Loading;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.main.screen.GameWindow;
import com.c4nn4.manager.controls.ControlListener;
import com.c4nn4.manager.controls.Controls;
import com.c4nn4.manager.debug.Debug;
import com.c4nn4.menu.MenuState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * GameStateManager
 * <p>
 * Manages the GameState and also passes information to the GameWindow.
 * Also saves options.
 *
 * @author IncroyablePix
 */
public class GameStateManager {
    private boolean paused;
    private final Loading loading;

    private final SaveSet save;

    private final GameWindow gw;

    private GameState currentState;
    private GameState previousState;

    //private int currentState, previousState;

    public GameStateManager(GameWindow gw, Constructor<GameState> firstState) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.gw = gw;
        paused = false;

        this.save = new SaveSet();

        this.loading = new Loading();

        setResolution(save.getResX(), save.getResY());
        fullScreenRelay(save.getFullscreen());
        setFPSLimit(save.getFPSLimit());

        setState(firstState.newInstance(this));

        Controls.get().addListener(new ControlListener() {
            @Override
            public void onKeyInputChanged(int down, int pressed, int released) {
                if ((pressed & Controls.DEBUG) == Controls.DEBUG)
                    Debug.get().setStatus(!Debug.get().getStatus());
            }

            @Override
            public boolean isPaused() {
                return false;
            }

            @Override
            public void pause(boolean paused) { }
        });
    }

    public int getXRes() {
        return gw.getXResolution();
    }

    public int getYRes() {
        return gw.getYResolution();
    }

    public void setResolution(final int x, final int y) {
        gw.setResolution(x, y);
        gw.setFullScreen(gw.isFullscreen());
        save.setResolution(x, y);
    }

    /*public void unloadState(int i) {
		gameStates[i] = null;
	}*/

    public void setState(GameState state) {
        if(state != null) {
            previousState = currentState;
            if (previousState != null)
                previousState.terminate();
            //unloadState(previousState);

            //currentState = state;

            currentState = state;

            currentState.init();
        }
    }

    public void update() {
        if (currentState != null) {
            currentState.update();
        }

        //debug.write("xMouse = " + Mouse.xMouse + " yMouse = " + Mouse.yMouse);
        //debug.write("LMB: " + Mouse.isDown(Mouse.LMB));
    }

    public void draw(final Renderer renderer) {
        if (paused) {
            currentState.draw(renderer);
        }
        else if (currentState != null) {
            if(currentState.isInitialized())
                currentState.draw(renderer);
            else
                loading.draw(renderer);
        }

        Debug.get().draw(renderer);//Debug
    }

    public void setMenuState(MenuState menu) {
        //if (currentState instanceof GameMenu) {
            currentState.setState(menu);
        //}
    }

    public void saveSettings() {
        save.saveFile();
    }

    public void fullScreenRelay(boolean fullscreen) {
        gw.setFullScreen(fullscreen);
        save.setFullScreen(fullscreen);
    }

    public void setFPSLimit(final boolean fpsLimit) {
        gw.setFPSLimit(fpsLimit);
    }

    public boolean getFPSLimit() {
        return gw.getFPSLimit();
    }

    public boolean getFullscreen() {
        return gw.isFullscreen();
    }

    public float getXFactor() {
        return gw.getXFactor();
    }

    public float getYFactor() {
        return gw.getYFactor();
    }

}
