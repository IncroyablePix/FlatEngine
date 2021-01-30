package be.helmo.main;

import be.helmo.main.screen.GameWindow;
import be.helmo.manager.debug.Debug;

public class GameThread implements Runnable {
    private static final int FPS = 30;
    private static final int TARGET_TIME = 1000 / FPS;
    private static final int TICKS_NANOS = TARGET_TIME * 1000000;

    public static double actionFactor = 0.0;
    private static int ticks = 0;
    private static boolean used = false;

    private final GameWindow gw;
    private boolean running;

    public GameThread(GameWindow gw) {
        this.gw = gw;
    }

    public static boolean tick(int periodicity) {
        return periodicity != 0 && ticks % periodicity == 0 && !used;
    }

    public static int ticksFrom(int tick) {
        //Debug.log("used: " + used);
        return /*used ? -1 : */ticks - tick;
    }

    public static int ticks() {
        return ticks;
    }

    @Override
    public void run() {
        gw.init();
        running = true;

        long tickTime = 0;
        long lastTick = System.nanoTime();

        long start;
        long elapsed;
        long wait;

        while (running) {
            start = System.nanoTime();

            gw.update();

            elapsed = System.nanoTime() - start;

            tickTime += elapsed;
            actionFactor = (double) elapsed / 1000000000;

            if (tickTime > TICKS_NANOS) {

                long addTicks = tickTime / TICKS_NANOS;
                lastTick = System.nanoTime() - lastTick;

                if (addTicks > 1)
                    Debug.log("Tick took too long!");

                tickTime -= addTicks * TICKS_NANOS;
                ticks += addTicks;
                used = false;
            }
            else {
                used = true;
            }
        }
    }
}
