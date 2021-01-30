package be.helmo.graphics.overimages;

import java.awt.*;

public class FadingImg extends Img {

    private final byte speed;

    public FadingImg(final int x, final int y, final int width, final int height, final int duration, final byte speed, final Image img) {
        super(x, y, width, height, duration, img, 1.f);

        this.speed = speed;
    }

    private void updateAlpha() {
        int maxTick = 0;
        int currentTick = getTicks();

        switch (speed) {
            case SPEED_SLOW: {
                maxTick = 30;
                break;
            }
            case SPEED_MEDIUM: {
                maxTick = 15;
                break;
            }
            case SPEED_FAST: {
                maxTick = 6;
                break;
            }
        }
        if (getDuration() == currentTick) {
            setAlpha(0.f);
        }
        else if (getDuration() != -1 && getDuration() - maxTick < currentTick) {
            int delta = getDuration() - currentTick;
            setAlpha((float) delta / maxTick);
        }
        else if (currentTick < maxTick) {
            setAlpha((float) currentTick / maxTick);
        }
        else {
            setAlpha(1.f);
        }
    }

    @Override
    public void update() {
        super.update();
        updateAlpha();
    }

}
