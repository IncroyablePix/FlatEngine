package be.helmo.manager.debug;

import be.helmo.graphics.render.Renderer;

public class Debug {

    private static final Debug debug = new Debug();

    private boolean status;

    private final DebugConsole dc;
    private final DebugFPS dfps;
    private final DebugInfo dinfo;

    public Debug() {
        status = false;
        dc = new DebugConsole();
        dfps = new DebugFPS();
        dinfo = new DebugInfo();
    }

    public static Debug get() {
        return debug;
    }

    public static DebugInfo getinfo() {
        return debug.dinfo;
    }

    public void draw(final Renderer renderer) {
        if (status) {
            dc.draw(renderer);
            dfps.draw(renderer);
            dinfo.draw(renderer);
        }
    }

    public void write(final String text) {
        dc.write(text);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public static void log(final String text) {
        debug.dc.write(text);
    }

    public static void setFPS(final int fps) {
        debug.dfps.setFPS(fps);
    }

    public static int getFPS() {
        return debug.dfps.getFps();
    }

}
