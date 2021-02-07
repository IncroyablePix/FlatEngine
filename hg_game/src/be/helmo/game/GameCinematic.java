package be.helmo.game;

import be.helmo.graphics.render.Renderer;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.GameStateManager;
import be.helmo.menu.MenuState;

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
