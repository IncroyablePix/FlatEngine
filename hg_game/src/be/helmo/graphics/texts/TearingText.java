package be.helmo.graphics.texts;

import be.helmo.graphics.Renderer;

import java.awt.*;

public class TearingText extends Text {

    private final Text[] parts;

    public TearingText(int x, int y, int duration, String textString, Color color, byte font) {
        super(x, y, duration, textString, color, font);

        parts = new Text[textString.length()];
    }


    @Override
    public void draw(final Renderer renderer) {
		/*drawer.setColor(getColor());
		drawer.drawString(xAdjusted, getShowYPos(), showMsg, font, getAlpha());*/
    }
}
