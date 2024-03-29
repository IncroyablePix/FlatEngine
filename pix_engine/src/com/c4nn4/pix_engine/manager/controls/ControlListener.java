package com.c4nn4.pix_engine.manager.controls;

public interface ControlListener {
    void onKeyInputChanged(int down, int pressed, int released);

    boolean isPaused();

    void pause(boolean paused);

    default void register() {
        Controls.get().addListener(this);
    }

    default void unregister() {
        Controls.get().removeListener(this);
    }
}
