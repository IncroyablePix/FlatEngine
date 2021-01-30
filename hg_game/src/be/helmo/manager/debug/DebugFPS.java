package be.helmo.manager.debug;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.texts.Text;
import be.helmo.manager.FontManager;

import java.awt.*;

public class DebugFPS {
    private int fps = 0;
    private final Text outFPS;

    public DebugFPS() {
        this.outFPS = new Text(1900, 1030, -1, "0", Color.RED, FontManager.COURIER_S);
        this.outFPS.setAlignement(Text.ALIGNEMENT_RIGHT);
    }

    protected void setFPS(final int fps) {
        this.fps = fps;
        this.outFPS.setText("" + fps);
    }

    protected int getFps() {
        return this.fps;
    }

    public void draw(final Renderer renderer) {
        this.outFPS.draw(renderer);
    }
}
