package be.helmo.graphics.texts;

import be.helmo.graphics.Speed;
import be.helmo.manager.fonts.Fonts;

import java.awt.*;

public class BlinkingText extends Text {
    private Speed speed;
    private float alpha;
    private float alphaChange;

    public BlinkingText(int x, int y, int duration, Speed speed, String textString, Color color, Fonts font) {
        super(x, y, duration, textString, color, font);
        this.speed = speed;
        this.alpha = 0.0f;
        alphaChange = alphaChange();
    }

    @Override
    public void update() {
        super.update();

        alpha += alphaChange;

        if(alpha >= 1.0f) {
            alpha = 1.0f;
            alphaChange = -alphaChange;
        }
        else if(alpha <= 0.f) {
            alpha = 0.0f;
            alphaChange = -alphaChange;
        }

        setAlpha(alpha);
    }

    private float alphaChange() {
        switch(speed) {
            default:
            case SLOW:
                return 0.00001f;
            case MEDIUM:
                return 0.000025f;
            case FAST:
                return 0.0001f;
        }
    }
}
