package com.c4nn4.pix_engine.manager.debug;

import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;

public class DebugFPS {
    private int fps = 0;
    private final Text outFPS;

    public DebugFPS() {
        this.outFPS = new Text(1900, 1030, -1, "0", Color.RED, Fonts.COURIER_S);
        this.outFPS.setAlignement(Alignement.RIGHT);
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
