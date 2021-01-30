package be.helmo.game;

import be.helmo.enums.GameMenus;
import be.helmo.graphics.Renderer;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(final Renderer renderer);

    public void debug(final String text) {
        Debug.get().write(text);
    }

    public abstract void setState(GameMenus menu);

    public abstract void terminate();
}
