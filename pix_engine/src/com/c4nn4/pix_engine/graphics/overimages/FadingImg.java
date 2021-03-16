package com.c4nn4.pix_engine.graphics.overimages;

import com.c4nn4.pix_engine.graphics.Speed;

import java.awt.*;

public class FadingImg extends Img {

    private final Speed speed;

    public FadingImg(final int x, final int y, final int width, final int height, final int duration, final Speed speed, final Image img) {
        super(x, y, width, height, duration, img, 1.f);

        this.speed = speed;
    }

    private void updateAlpha() {
        int maxTick = 0;
        int currentTick = getTicks();

        switch (speed) {
            case SLOW: {
                maxTick = 30;
                break;
            }
            case MEDIUM: {
                maxTick = 15;
                break;
            }
            case FAST: {
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
