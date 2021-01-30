package be.helmo.graphics;

import java.awt.*;

public interface Renderer {
    void drawString(final int x, final int y, final String text, final byte font);

    void drawString(int x, int y, final String text, final byte font, final float alpha);

    void drawRectangle(final int x, final int y, final int width, final int height, final Color color);

    void drawImage(final Image img, final int x, final int y, final int width, final int height, final float alpha);

    void setFont(final byte font);

    void setAlpha(final float alpha);

    void setColor(final Color color);

    void refreshDrawer();

    int[] getPixels();

    void render();

    void clear();
}
