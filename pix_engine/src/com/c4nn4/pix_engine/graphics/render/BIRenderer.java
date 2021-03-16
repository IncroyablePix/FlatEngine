package com.c4nn4.pix_engine.graphics.render;

import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;

public class BIRenderer implements Renderer {

    private final int width;
    private final int height;
    private final int[] pixels;

    public BIRenderer(int width, int height) {
        this.width = width;
        this.height = height;

        this.pixels = new int[width * height];
    }

    @Override
    public void drawString(int x, int y, String text, Fonts font) {

    }

    @Override
    public void drawString(int x, int y, String text, Fonts font, float alpha) {

    }

    @Override
    public void drawString(int x, int y, String text, Fonts font, float alpha, float stroke, Color strokeColor) {

    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color) {

    }

    @Override
    public void drawImage(Image img, int x, int y, int width, int height, float alpha) {

    }

    @Override
    public void setFont(Fonts font) {

    }

    @Override
    public void setAlpha(float alpha) {

    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void refreshDrawer() {

    }

    @Override
    public int[] getPixels() {
        return this.pixels;
    }

    @Override
    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Sprite sprite = PixManager.get().grass;
                //pixels[x + y * height] = ssPixels[(x + this.x) * (y + this.y) * sheet.getHeight()];
                //pixels[x + y * width] = sprite.pixels[(x % sprite.getWidth()) + (y % sprite.getHeight()) * sprite.getWidth()];//0xFF00FF;
            }
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0x000000;
    }
}
