package com.c4nn4.game;

import com.c4nn4.level.HigherGroundsLevel;
import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.overimages.Img;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.level.LevelEndListener;
import com.c4nn4.pix_engine.main.GameThread;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.audio.AudioManager;
import com.c4nn4.pix_engine.manager.controls.ControlListener;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.menu.MenuGameOver;
import com.c4nn4.pix_engine.manager.image.Content;
import com.c4nn4.pix_engine.menu.MenuState;

public class GamePlay extends GameState implements LevelEndListener {

    private HigherGroundsLevel level;
    private final Img pause;
    private boolean paused;
    private final GamePlayControlsHandler controlsHandler;

    public GamePlay(GameStateManager gsm) {
        super(gsm);

        pause = new Img(710, 100 + (Screen.WIN_HEIGHT / 2), 500, 200, -1, Content.load("/com/c4nn4/resources/Graphics/Others/PAUSE.png"), 1.0f);
        controlsHandler = new GamePlayControlsHandler();
        Controls.get().addListener(controlsHandler);

        debug("Loading GamePlay...");

        AudioManager.get().load("/com/c4nn4/resources/Sound/SFX/drawning.wav", "drawn");
        paused = false;
    }

    @Override
    public void init() {
        Debug.log("GamePlay init");

        level = new HigherGroundsLevel(1);
        subscribe(level);
        initialized = true;
    }

    @Override
    public void update() {
        //gsm.setState(GameStates.MENU);
        //gsm.setMenuState(GameMenus.MAIN_MENU);
        this.controlsHandler.pause(false);

        if(this.controlsHandler.paused) {
            pause.update();
        }
        else {
            if (level != null) {
                level.update();
            }
        }
    }

    @Override
    public void draw(final Renderer renderer) {
        if (level != null) {
            level.draw(renderer);
        }
        if(this.controlsHandler.paused) {
            pause.draw(renderer);
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
        this.level = new HigherGroundsLevel(level + 1);
        this.level.subscribe(this);
        AudioManager.get().unload("cat");
    }

    @Override
    public void subscribe(HigherGroundsLevel level) {
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
                setPaused(!GamePlay.this.paused);
                //pause(true);
            }
        }

        @Override
        public boolean isPaused() {
            return false;
        }

        @Override
        public void pause(boolean paused) {
            this.paused = false;
        }
    }

    private void setPaused(boolean paused) {
        if(paused) {
            pause.setPos(710, Screen.WIN_HEIGHT + 200);
            pause.setVelocity(0, -750.0);
            this.paused = paused;
        }
    }
}
