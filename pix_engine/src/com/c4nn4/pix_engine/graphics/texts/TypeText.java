package com.c4nn4.pix_engine.graphics.texts;

import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;


public class TypeText extends Text {
    private final Speed speed;
    private boolean displayable;

    public TypeText(final int x, final int y, final int duration, final Speed speed, final String textString, final Color color, final Fonts font) {
        super(x, y, duration, textString, color, font);

        this.speed = speed;
        displayable = false;
    }

    private void updateText() {
        final String currentText = super.getShowText();

        float letters = 0;
        int currentTick = getTicks();
        String text = "";

        switch (speed) {
            case SLOW: {
                letters = 0.5f;
                break;
            }
            case MEDIUM: {
                letters = 1.f;
                break;
            }
            case FAST: {
                letters = 1.5f;
            }
        }

        int endSub = (int) (currentTick * letters);
        if (endSub > getText().length()) {
            endSub = getText().length();
        }

        int begSub = 0;
        int delta;
        if ((delta = getDuration() - getTicks()) < 20) {
            int len = getText().length();
            begSub = delta == 0 ? len : len - ((len * delta) / 20);
        }

        if (begSub > endSub) {
            begSub = endSub;
        }

        if (begSub > 0) {
            for (int i = 0; i < begSub; i++) text += " ";
        }

        text += getText().substring(begSub, endSub);

        if (!text.equals(currentText)) {
            setShowText(text);
        }
    }

    @Override
    public void update() {
        super.update();

        if (!displayable)
            displayable = true;

        updateText();
    }

    @Override
    public void draw(Renderer renderer) {
        if (displayable) {
            super.draw(renderer);
        }
    }

}
