package com.c4nn4.manager.controls.mouse;

import com.c4nn4.graphics.texts.Alignement;

public class ClickZone {
    public final int x, y;
    public final int width, height;

    private Alignement alignement;

    public ClickZone(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.alignement = Alignement.LEFT;
    }

    public void setAlignement(Alignement alignement) { this.alignement = alignement; }

    public boolean within(final int x, final int y) {
        switch(alignement) {
            default:
            case LEFT:
                return this.x + this.width >= x && x >= this.x &&
                        this.y + this.height >= y && y >= this.y;
            case CENTER:
                return (this.x + this.width / 2) >= x && x >= (this.x - this.width / 2) &&
                        this.y + this.height >= y && y >= this.y;
            case RIGHT:
                return this.x >= x && x >= this.x - this.width &&
                        this.y + this.height >= y && y >= this.y;

        }
    }

    public ClickPoint clickOn(final int x, final int y) {
        return new ClickPoint(x - this.x, y - this.y);
    }
}
