package com.c4nn4.game;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.manager.debug.Debug;
import com.c4nn4.manager.GameStateManager;
import com.c4nn4.menu.MenuState;

public abstract class GameState {
    protected GameStateManager gsm;
    protected boolean initialized;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        this.initialized = false;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(final Renderer renderer);

    public void debug(final String text) {
        Debug.get().write(text);
    }

    public abstract void setState(MenuState menu);

    public abstract void terminate();

    public final boolean isInitialized() {
        return this.initialized;
    }
}
