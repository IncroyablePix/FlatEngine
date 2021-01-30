package be.helmo.manager;

import be.helmo.enums.GameMenus;
import be.helmo.enums.GameStates;
import be.helmo.game.*;
import be.helmo.graphics.Renderer;
import be.helmo.main.screen.GameWindow;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.debug.Debug;

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
    private final GamePause pause;

    private final SaveSet save;

    private final GameWindow gw;

    private GameState currentState;
    private GameState previousState;

    //private int currentState, previousState;

    public GameStateManager(GameWindow gw) {
        this.gw = gw;
        paused = false;

        this.save = new SaveSet();

        this.pause = new GamePause(this);

        setResolution(save.getResX(), save.getResY());
        fullScreenRelay(save.getFullscreen());
        setFPSLimit(save.getFPSLimit());

        setState(GameStates.INTRO);

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

    public float getCoef() {
        return gw.getCoef();
    }
	
	/*public void unloadState(int i) {
		gameStates[i] = null;
	}*/

    public void setPaused(boolean b) {
        if (paused = b)
            pause.init();
    }

    public void setState(GameStates state) {
        previousState = currentState;
        if (previousState != null)
            previousState.terminate();
        //unloadState(previousState);

        //currentState = state;

        switch (state) {
            case INTRO: {
                currentState = new GameIntro(this);
                break;
            }
            case MENU: {
                currentState = new GameMenu(this);
                break;
            }
            case PLAY: {
                currentState = new GamePlay(this);
                break;
            }
            case DEBUG: {
                currentState = new GameDebug(this);
                break;
            }
            case GAME_OVER: {
                currentState = new GameOver(this);
                break;
            }
        }

        currentState.init();
    }

    public void update() {
        if (paused) {
            pause.update();
        }
        else if (currentState != null) {
            currentState.update();
        }

        //debug.write("xMouse = " + Mouse.xMouse + " yMouse = " + Mouse.yMouse);
        //debug.write("LMB: " + Mouse.isDown(Mouse.LMB));
    }

    public void draw(final Renderer renderer) {
        if (paused) {
            currentState.draw(renderer);
            pause.draw(renderer);
        }
        else if (currentState != null) {
            currentState.draw(renderer);
        }

        Debug.get().draw(renderer);//Debug
    }

    public void setMenuState(GameMenus menu) {
        if (currentState instanceof GameMenu) {
            currentState.setState(menu);
        }
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
