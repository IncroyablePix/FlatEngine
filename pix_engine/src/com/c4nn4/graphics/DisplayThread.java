package com.c4nn4.graphics;

import com.c4nn4.main.screen.GameWindow;
import com.c4nn4.manager.debug.Debug;

public class DisplayThread implements Runnable {

    private final int FPS = 30;
    private final int TARGET_TIME = 1000 / FPS;

    private boolean fpsLimit;
    private int fps = 0;

    private final GameWindow gw;

    public DisplayThread(final GameWindow gw) {
        this.gw = gw;
        fpsLimit = true;
    }

    @Override
    public void run() {

        long start;
        long elapsed;
        long wait;
        long lastfps = System.nanoTime();

        while (true) {

            start = System.nanoTime();

            gw.render();

            elapsed = System.nanoTime() - start;

            wait = TARGET_TIME - elapsed / 1000000;

            if (wait < 0) wait = TARGET_TIME;


            if (fpsLimit) {
                try {
                    Thread.sleep(wait);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            fps++;

            if (System.nanoTime() - lastfps > 1000000000) {
                lastfps = System.nanoTime();
                Debug.setFPS(fps);
                gw.setFPS(fps);
                fps = 0;
            }
        }
    }

    public void setFPSLimit(final boolean fpsLimit) {
        if (fpsLimit != this.fpsLimit) {
            this.fpsLimit = fpsLimit;
        }
    }

    public boolean getFPSLimit() {
        return this.fpsLimit;
    }

    public int getFPS() {
        return this.fps;
    }

}
