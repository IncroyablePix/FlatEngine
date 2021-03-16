package com.c4nn4.pix_engine.graphics.sprites;

public class Sprite {
    /*private final int x;
    private final int y;
    private final int width;
    private final int height;
    public int[] pixels;
    private final SpriteSheet sheet;

    public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
        this.width = width;
        this.height = height;

        this.x = x * width;
        this.y = y * height;

        this.sheet = sheet;
        this.pixels = new int[width * height];
        load();
    }

    private void load() {
        int[] ssPixels = sheet.getPixels();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = ssPixels[(x + this.x) + (y + this.y) * sheet.getWidth()];
            }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }*/
}
