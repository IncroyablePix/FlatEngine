package com.c4nn4.graphics.texts;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.manager.fonts.Fonts;

import java.awt.*;

public class TearingText extends Text {

    private final Text[] parts;

    public TearingText(int x, int y, int duration, String textString, Color color, Fonts font) {
        super(x, y, duration, textString, color, font);

        parts = new Text[textString.length()];
    }


    @Override
    public void draw(final Renderer renderer) {
		/*drawer.setColor(getColor());
		drawer.drawString(xAdjusted, getShowYPos(), showMsg, font, getAlpha());*/
    }
}