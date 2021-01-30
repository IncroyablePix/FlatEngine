package be.helmo.graphics.texts;

import java.awt.*;

public class BouncingText extends Text {

    public BouncingText(final int x, final int y, final int height, final int duration, final byte speed, final String textString, final Color color, final byte font) {
        super(x, y, duration, textString, color, font);

        setShowYPosition(y + height);

        switch (speed) {
            case SPEED_SLOW: {
                setYVel(-2);
                break;
            }
            case SPEED_MEDIUM: {
                setYVel(-4);
                break;
            }
            case SPEED_FAST: {
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
