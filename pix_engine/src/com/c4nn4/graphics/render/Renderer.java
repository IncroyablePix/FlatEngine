package com.c4nn4.graphics.render;

import com.c4nn4.manager.fonts.Fonts;

import java.awt.*;

public interface Renderer {
    void drawString(final int x, final int y, final String text, final Fonts font);

    void drawString(int x, int y, final String text, final Fonts font, final float alpha);

    void drawString(int x, int y, final String text, final Fonts font, final float alpha, final float stroke, final Color strokeColor);

    void drawRectangle(final int x, final int y, final int width, final int height, final Color color);

    void drawImage(final Image img, final int x, final int y, final int width, final int height, final float alpha);

    void setFont(final Fonts font);

    void setAlpha(final float alpha);

    void setColor(final Color color);

    void refreshDrawer();

    int[] getPixels();

    void render();

    void clear();
}
