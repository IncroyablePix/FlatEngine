package com.c4nn4.manager.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    /*private final int width, height;
    private final int[] pixels;*/

    private String name;
    private BufferedImage image;
    private Sprite[] sprites;

    static SpriteSheet loadSpriteSheet(String path, String name, int spriteWidth, int spriteHeight) {

        SpriteSheet ss;

        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            ss = new SpriteSheet(name, image);
            ss.cutDown(spriteWidth, spriteHeight);
        }
        catch (IOException | IllegalArgumentException e) {
            ss = null;
        }

        return ss;
    }

    private SpriteSheet(String name, BufferedImage image) {
        this.name = name;
        this.image = image;

        /*this.pixels = new int[image.getHeight() * image.getHeight()];
        this.width = image.getWidth();
        this.height = image.getHeight();*/

        //image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
    }

    public String getName() {
        return name;
    }

    /**
     * Cuts a spritesheet in several smaller sprites
     *  => Cuts the sheet from left to right, from top to bottom.
     *
     * @param w The width of one sprite
     * @param h The height of one sprite
     *
     * @return An array of sprites
     */
    private void cutDown(int w, int h) {
        int width = image.getWidth() / w;
        int height = image.getHeight() / h;

        sprites = new Sprite[width * height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pos =  i + j * width;
                sprites[pos] = new Sprite(getName() + "_" + pos, image.getSubimage(i * w, j * h, w, h));
            }
        }
    }

    Sprite getSprite(int index) {
        try {
            return sprites[index];
        }
        catch(ArrayIndexOutOfBoundsException e) {
            throw new SpriteNotFoundException("Sprite index could not be found among spritesheet's");
        }
    }
}
