package com.c4nn4.pix_engine.manager.image;

import java.awt.image.BufferedImage;

public class Sprite {
    private final String name;
    private final BufferedImage image;

    Sprite(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }
}
