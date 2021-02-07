package be.helmo.game;

import be.helmo.menu.GameMenus;
import be.helmo.graphics.render.Renderer;
import be.helmo.level.GameLevel;
import be.helmo.level.LevelEndListener;
import be.helmo.main.GameThread;
import be.helmo.manager.audio.AudioManager;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.GameStateManager;
import be.helmo.menu.MenuGameOver;
import be.helmo.menu.MenuState;

public class GamePlay extends GameState implements LevelEndListener {

    private final int ticks;

    private GameLevel level;
    private final GamePlayControlsHandler controlsHandler;

    public GamePlay(GameStateManager gsm) {
        super(gsm);

        controlsHandler = new GamePlayControlsHandler();
        Controls.get().addListener(controlsHandler);

        ticks = GameThread.ticks();
        debug("Loading GamePlay...");

        AudioManager.get().load("/be/helmo/resources/Sound/SFX/drawning.wav", "drawn");
    }

    @Override
    public void init() {
        Debug.log("GamePlay init");

        level = new GameLevel(1);
        subscribe(level);
        initialized = true;
    }

    @Override
    public void update() {
        //gsm.setState(GameStates.MENU);
        //gsm.setMenuState(GameMenus.MAIN_MENU);
        this.controlsHandler.pause(false);

        if (level != null) {
            level.update();
        }
    }

    @Override
    public void draw(final Renderer renderer) {
        if (level != null) {
            level.draw(renderer);
        }
    }

    @Override
    public void setState(MenuState menu) {
    }

    @Override
    public void terminate() {
        Controls.get().removeListener(controlsHandler);
    }

    @Override
    public void onLevelFinishes(int level) {
        Debug.log("Level " + level + " cleared");
        this.level = new GameLevel(level + 1);
        this.level.subscribe(this);
        AudioManager.get().unload("cat");
    }

    @Override
    public void subscribe(GameLevel level) {
        level.subscribe(this);
    }

    @Override
    public void onGameOver() {
        gsm.setState(new GameMenu(gsm));
        gsm.setMenuState(new MenuGameOver(gsm, null));
    }

    private class GamePlayControlsHandler implements ControlListener {
        private boolean paused = false;

        @Override
        public void onKeyInputChanged(int down, int pressed, int released) {
            if ((pressed & Controls.PAUSE) == Controls.PAUSE) {
                Debug.log("Pause in play");
                gsm.setPaused(true);
                pause(true);
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
