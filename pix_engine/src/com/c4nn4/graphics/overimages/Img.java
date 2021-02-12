package com.c4nn4.graphics.overimages;

import com.c4nn4.graphics.Element;
import com.c4nn4.graphics.render.Renderer;

import java.awt.*;

public class Img extends Element {

    private final Image img;

    private final int width;
    private final int height;

    public Img(final int x, final int y, final int width, final int height, final int duration, final Image img, final float alpha) {
        super(x, y, duration);
        setAlpha(alpha);

        this.img = img;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.drawImage(img, (int) getShowXPos(), (int) getShowYPos(), width, height, getAlpha());
    }

    public void setVelocity(final double x, final double y) {
        this.setXVel(x);
        this.setYVel(y);
    }


    public double getX() {
        return this.getShowXPos();
    }

    public double getY() {
        return this.getShowYPos();
    }

    public double getXVel() {
        return super.getXVel();
    }

    public double getYVel() {
        return super.getYVel();
    }
}
