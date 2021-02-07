package be.helmo.graphics.texts;

import be.helmo.graphics.Speed;
import be.helmo.manager.fonts.Fonts;

import java.awt.*;

public class BouncingText extends Text {

    public BouncingText(final int x, final int y, final int height, final int duration, final Speed speed, final String textString, final Color color, final Fonts font) {
        super(x, y, duration, textString, color, font);

        setShowYPosition(y + height);

        switch (speed) {
            case SLOW: {
                setYVel(-2);
                break;
            }
            case MEDIUM: {
                setYVel(-4);
                break;
            }
            case FAST: {
                setYVel(-6);
                break;
            }
        }
    }

    @Override
    public void update() {
        super.update();
    }

}
