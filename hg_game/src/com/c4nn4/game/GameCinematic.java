package com.c4nn4.game;

import com.c4nn4.pix_engine.game.GameState;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.menu.MenuState;

/**
 * GameCinematic
 *
 * @author IncroyablePix
 */
public class GameCinematic extends GameState {

    public GameCinematic(final GameStateManager gsm, final Debug debug) {
        super(gsm);
    }

    @Override
    public void init() {

        initialized = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Renderer renderer) {

    }

    @Override
    public void setState(MenuState menu) {

    }

    @Override
    public void terminate() {

    }

}
